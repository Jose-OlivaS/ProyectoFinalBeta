package com.example.proyectofinalbackend.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "vehicles")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String year;
    private String licensePlate;
    private BigDecimal dailyRate;
    private boolean available;

    @OneToMany(mappedBy = "vehicle")
    private Set<Reserva> reservations;

    public boolean isAvailable(Vehiculo vehiculo) {
        return false;
    }

    public String getBrand(Vehiculo vehiculo) {
        return "";
    }
}