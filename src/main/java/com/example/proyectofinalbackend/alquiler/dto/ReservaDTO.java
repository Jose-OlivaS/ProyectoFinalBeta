package com.example.proyectofinalbackend.alquiler.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservaDTO {
    private String username;
    private Long vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public String getStatus() {
        return "";
    }
}
