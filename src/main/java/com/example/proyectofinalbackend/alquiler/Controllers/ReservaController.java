package com.example.proyectofinalbackend.alquiler.Controllers;


import com.example.proyectofinalbackend.alquiler.dto.ReservaDTO;
import com.example.proyectofinalbackend.alquiler.model.Reserva;
import com.example.proyectofinalbackend.alquiler.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservaController {
    @Autowired
    private ReservaService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservaDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody ReservaDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.updateReservation(id, reservationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reserva>> getUserReservations(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/active")
    public ResponseEntity<?> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports/monthly")
    public ResponseEntity<?> getMonthlyReservationsReport() {
        return ResponseEntity.ok(reservationService.getMonthlyReport());
    }
}