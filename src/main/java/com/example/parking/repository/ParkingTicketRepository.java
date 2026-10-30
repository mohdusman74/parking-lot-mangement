package com.example.parking.repository;
import com.example.parking.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {}
