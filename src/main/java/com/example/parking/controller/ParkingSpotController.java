package com.example.parking.controller;
import com.example.parking.dto.*;
import com.example.parking.entity.VehicleType;
import com.example.parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/spots")
public class ParkingSpotController {
    private final ParkingService service;
    public ParkingSpotController(ParkingService service) { this.service = service; }
    @PostMapping
    public SpotResponse create(@Valid @RequestBody CreateSpotRequest request) {
        return service.createSpot(request);
    }
    @GetMapping
    public List<SpotResponse> getAll() { return service.getAllSpots(); }
    @GetMapping("/available-count")
    public AvailableCountResponse availableCount(@RequestParam VehicleType type) {
        return new AvailableCountResponse(type, service.countAvailableSpots(type));
    }
    public record AvailableCountResponse(VehicleType type, long availableCount) {}
}
