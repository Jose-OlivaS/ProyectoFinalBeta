package com.example.proyectofinalbackend.alquiler.Repository;


import com.example.proyectofinalbackend.alquiler.model.Reserva;
import com.example.proyectofinalbackend.alquiler.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUserId(Long userId);

    default List<Reserva> findByVehicleId() {
        return findByVehicleId(null);
    }

    default List<Reserva> findByVehicleId(Long vehicleId) {
        return null;
    }}