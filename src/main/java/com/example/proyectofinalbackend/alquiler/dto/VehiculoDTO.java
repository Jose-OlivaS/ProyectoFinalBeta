package com.example.proyectofinalbackend.alquiler.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VehiculoDTO {
    private String brand;
    private String model;
    private String year;
    private String licensePlate;
    private BigDecimal dailyRate;
}
