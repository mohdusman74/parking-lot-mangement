package com.example.parking.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "parking_tickets")
public class ParkingTicket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30) private String licensePlate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private VehicleType vehicleType;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpot spot;
    @Column(nullable = false) private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Double fee;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private TicketStatus status = TicketStatus.ACTIVE;
    protected ParkingTicket() {}
    public ParkingTicket(String licensePlate, VehicleType vehicleType, ParkingSpot spot) {
        this.licensePlate = licensePlate; this.vehicleType = vehicleType;
        this.spot = spot; this.entryTime = LocalDateTime.now(); this.status = TicketStatus.ACTIVE;
    }
    public void close(LocalDateTime exitTime, double fee) {
        if (status == TicketStatus.CLOSED) throw new IllegalStateException("Ticket is already closed");
        this.exitTime = exitTime; this.fee = fee; this.status = TicketStatus.CLOSED;
    }
    public Long getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getVehicleType() { return vehicleType; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public Double getFee() { return fee; }
    public TicketStatus getStatus() { return status; }
}
