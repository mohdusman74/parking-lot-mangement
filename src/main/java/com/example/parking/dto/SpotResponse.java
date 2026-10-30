package com.example.parking.dto;
import com.example.parking.entity.*;
public record SpotResponse(Long id, VehicleType type, SpotStatus status) {
    public static SpotResponse from(ParkingSpot s) {
        return new SpotResponse(s.getId(), s.getType(), s.getStatus());
    }
}
