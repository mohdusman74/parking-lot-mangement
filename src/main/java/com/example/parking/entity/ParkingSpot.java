package com.example.parking.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "parking_spots")
public class ParkingSpot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private VehicleType type;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private SpotStatus status = SpotStatus.AVAILABLE;
    protected ParkingSpot() {}
    public ParkingSpot(VehicleType type) { this.type = type; this.status = SpotStatus.AVAILABLE; }
    public Long getId() { return id; }
    public VehicleType getType() { return type; }
    public SpotStatus getStatus() { return status; }
    public void occupy() {
        if (status != SpotStatus.AVAILABLE) throw new IllegalStateException("Spot is not available");
        status = SpotStatus.OCCUPIED;
    }
    public void release() { status = SpotStatus.AVAILABLE; }
}
