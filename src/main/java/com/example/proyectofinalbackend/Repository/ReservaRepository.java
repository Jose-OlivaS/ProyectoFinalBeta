package com.example.proyectofinalbackend.Repository;

import com.example.proyectofinalbackend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByClienteId(Long clienteId);
    List<Reserva> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
}
