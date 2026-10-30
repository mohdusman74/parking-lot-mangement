package com.example.parking.service;
import com.example.parking.dto.*;
import com.example.parking.entity.*;
import com.example.parking.exception.*;
import com.example.parking.kafka.KafkaProducer;
import com.example.parking.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class ParkingService {
    private final ParkingSpotRepository spotRepository;
    private final ParkingTicketRepository ticketRepository;
    private final KafkaProducer kafkaProducer;
    public ParkingService(ParkingSpotRepository spotRepository, ParkingTicketRepository ticketRepository, KafkaProducer kafkaProducer) {
        this.spotRepository = spotRepository;
        this.ticketRepository = ticketRepository;
        this.kafkaProducer = kafkaProducer;
    }
    @Transactional
    public SpotResponse createSpot(CreateSpotRequest request) {
        return SpotResponse.from(spotRepository.save(new ParkingSpot(request.type())));
    }
    @Transactional(readOnly = true)
    public List<SpotResponse> getAllSpots() {
        return spotRepository.findAllByOrderByIdAsc().stream().map(SpotResponse::from).toList();
    }
    @Transactional(readOnly = true)
    public long countAvailableSpots(VehicleType type) {
        return spotRepository.countByTypeAndStatus(type, SpotStatus.AVAILABLE);
    }
    @Transactional
    public TicketResponse vehicleEntry(EntryRequest request) {
        List<ParkingSpot> available = spotRepository.findAvailableSpotsForUpdate(
            request.vehicleType(), SpotStatus.AVAILABLE);
        if (available.isEmpty()) throw new NoSpotAvailableException(
            "No available spot for vehicle type: " + request.vehicleType());
        ParkingSpot spot = available.get(0);
        spot.occupy();
        ParkingTicket ticket = new ParkingTicket(
            request.licensePlate().trim().toUpperCase(), request.vehicleType(), spot);
        spotRepository.save(spot);
        kafkaProducer.sendMessage(ticket.getLicensePlate());
        return TicketResponse.from(ticketRepository.save(ticket));
    }
    @Transactional(readOnly = true)
    public TicketResponse getTicket(Long id) {
        ParkingTicket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found: " + id));

        return TicketResponse.from(ticket);
    }
    @Transactional
    public TicketResponse vehicleExit(Long id) {
        ParkingTicket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found: " + id));
        if (ticket.getStatus() == TicketStatus.CLOSED)
            throw new IllegalStateException("Ticket is already closed");
        LocalDateTime exit = LocalDateTime.now();
        long minutes = Math.max(0, Duration.between(ticket.getEntryTime(), exit).toMinutes());
        long hours = Math.max(1, (minutes + 59) / 60);
        double hourlyRate = ticket.getVehicleType() == VehicleType.CAR ? 50.0 : 20.0;
        ticket.close(exit, hours * hourlyRate);
        ticket.getSpot().release();
        return TicketResponse.from(ticket);
    }
}
