package com.example.parking.dto;
import com.example.parking.entity.VehicleType;
import jakarta.validation.constraints.*;
public record EntryRequest(
    @NotBlank @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "Use letters, numbers, or hyphens in license plate")
    String licensePlate,
    @NotNull VehicleType vehicleType
) {}
