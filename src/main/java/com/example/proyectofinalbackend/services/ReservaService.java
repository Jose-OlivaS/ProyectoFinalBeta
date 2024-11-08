package com.example.proyectofinalbackend.services;

import com.example.proyectofinalbackend.Repository.ReservaRepository;
import com.example.proyectofinalbackend.Repository.VehiculoRepository;
import com.example.proyectofinalbackend.model.Reserva;
import com.example.proyectofinalbackend.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Reserva crearReserva(Reserva reserva) {
        Vehiculo vehiculo = reserva.getVehiculo();
        vehiculo.setDisponible(false);
        vehiculoRepository.save(vehiculo);
        return reservaRepository.save(reserva);
    }

    public Reserva modificarReserva(Long id, Reserva reserva) {
        Reserva existente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        existente.setFechaInicio(reserva.getFechaInicio());
        existente.setFechaFin(reserva.getFechaFin());
        return reservaRepository.save(existente);
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado("CANCELADA");
        Vehiculo vehiculo = reserva.getVehiculo();
        vehiculo.setDisponible(true);
        vehiculoRepository.save(vehiculo);
        reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerReservasCliente(Long clienteId) {
        return reservaRepository.findByClienteId(clienteId);
    }

    public List<Reserva> obtenerReservasPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return List.of();
    }
}