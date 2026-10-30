package com.example.parking.dto;
import com.example.parking.entity.*;
import java.time.LocalDateTime;
public record TicketResponse(Long ticketId, String licensePlate, VehicleType vehicleType,
    Long spotId, LocalDateTime entryTime, LocalDateTime exitTime, Double fee, TicketStatus status) {
    public static TicketResponse from(ParkingTicket t) {
        return new TicketResponse(t.getId(), t.getLicensePlate(), t.getVehicleType(),
            t.getSpot().getId(), t.getEntryTime(), t.getExitTime(), t.getFee(), t.getStatus());
    }
}
