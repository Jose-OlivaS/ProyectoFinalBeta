package com.example.proyectofinalbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // PENDIENTE, ACTIVA, FINALIZADA, CANCELADA
    private double montoTotal;

    public double getMonto() {
        return 0;
    }
}