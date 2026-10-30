package com.example.parking.dto;
import com.example.parking.entity.VehicleType;
import jakarta.validation.constraints.NotNull;
public record CreateSpotRequest(@NotNull VehicleType type) {}
