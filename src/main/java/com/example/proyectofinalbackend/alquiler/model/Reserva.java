package com.example.proyectofinalbackend.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservations")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuario user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehiculo vehicle;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // PENDING, ACTIVE, COMPLETED, CANCELLED
    private BigDecimal totalCost;
}