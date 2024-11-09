package com.example.proyectofinalbackend.alquiler.services;

import com.example.proyectofinalbackend.alquiler.dto.ReservaDTO;
import com.example.proyectofinalbackend.alquiler.model.Reserva;
import com.example.proyectofinalbackend.alquiler.model.Vehiculo;
import com.example.proyectofinalbackend.alquiler.Repository.ReservaRepository;
import com.example.proyectofinalbackend.alquiler.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservationRepository;

    @Autowired
    private VehiculoRepository vehicleRepository;

    @Autowired
    private UsuarioService userService;

    public Reserva createReservation(ReservaDTO reservationDTO) {
        Vehiculo vehicle = vehicleRepository.findById(reservationDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehículo no disponible");
        }

        Reserva reservation = new Reserva();
        reservation.setUser(userService.findByUsername(reservationDTO.getUsername()));
        reservation.setVehicle(vehicle);
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus("PENDING");

        // Calcular costo total
        long days = ChronoUnit.DAYS.between(reservationDTO.getStartDate(), reservationDTO.getEndDate());
        reservation.setTotalCost(vehicle.getDailyRate().multiply(new BigDecimal(days)));

        vehicle.setAvailable(false);
        vehicleRepository.save(vehicle);

        return reservationRepository.save(reservation);
    }

    public Reserva updateReservation(Long id, ReservaDTO dto) {
        Reserva reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Validar que la reserva pertenezca al usuario o sea admin (ajustar según permisos)
        updateReservationFromDTO(reservation, dto);
        return reservationRepository.save(reservation);
    }

    private void updateReservationFromDTO(Reserva reservation, ReservaDTO dto) {
        if (dto.getStartDate() != null) {
            reservation.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            reservation.setEndDate(dto.getEndDate());
        }
        if (dto.getStatus() != null) {
            reservation.setStatus(dto.getStatus());
        }
    }

    public void cancelReservation(Long id) {
        Reserva reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservation.setStatus("CANCELLED");
        reservation.getVehicle().setAvailable(true);
        vehicleRepository.save(reservation.getVehicle());
        reservationRepository.save(reservation);
    }

    public List<Reserva> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reserva> getActiveReservations() {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStatus().equals("ACTIVE"))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getMonthlyReport() {
        List<Reserva> allReservations = reservationRepository.findAll();
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);

        List<Reserva> monthlyReservations = allReservations.stream()
                .filter(r -> r.getStartDate().isAfter(monthAgo))
                .collect(Collectors.toList());

        return Map.of(
                "totalReservations", monthlyReservations.size(),
                "totalRevenue", calculateTotalRevenue(monthlyReservations),
                "reservationsByStatus", getReservationsByStatus(monthlyReservations)
        );
    }

    private BigDecimal calculateTotalRevenue(List<Reserva> reservations) {
        return reservations.stream()
                .map(Reserva::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, Long> getReservationsByStatus(List<Reserva> reservations) {
        return reservations.stream()
                .collect(Collectors.groupingBy(Reserva::getStatus, Collectors.counting()));
    }
}
