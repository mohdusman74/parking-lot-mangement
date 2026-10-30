package com.example.parking.controller;

import com.example.parking.dto.*;
import com.example.parking.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @PostMapping("/entry")
    public TicketResponse entry(@Valid @RequestBody EntryRequest request) {
        return service.vehicleEntry(request);
    }

    @GetMapping("/tickets/{ticketId}")
    public TicketResponse getTicket(@PathVariable Long ticketId) {
        return service.getTicket(ticketId);
    }

    @PostMapping("/exit/{ticketId}")
    public TicketResponse exit(@PathVariable Long ticketId) {
        return service.vehicleExit(ticketId);
    }
}
