package com.example.proyectofinalbackend.Controllers;
import com.example.proyectofinalbackend.model.Reserva;
import com.example.proyectofinalbackend.model.Vehiculo;
import com.example.proyectofinalbackend.services.UsuarioService;
import com.example.proyectofinalbackend.services.VehiculoService;
import com.example.proyectofinalbackend.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/reportes/alquileres")
    public Map<String, Object> reporteAlquileres(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {
        List<Reserva> reservas = reservaService.obtenerReservasPorFecha(fechaInicio, fechaFin);

        // Procesamiento con Streams para generar estadísticas
        long totalReservas = reservas.size();
        double ingresoTotal = reservas.stream()
                .mapToDouble(Reserva::getMonto)
                .sum();

        Map<String, Long> reservasPorEstado = reservas.stream()
                .collect(Collectors.groupingBy(
                        Reserva::getEstado,
                        Collectors.counting()
                ));

        return Map.of(
                "totalReservas", totalReservas,
                "ingresoTotal", ingresoTotal,
                "reservasPorEstado", reservasPorEstado
        );
    }

    @GetMapping("/reportes/disponibilidad")
    public Map<String, Long> reporteDisponibilidad() {
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodos();
        return vehiculos.stream()
                .collect(Collectors.groupingBy(
                        v -> v.isDisponible() ? "disponibles" : "ocupados",
                        Collectors.counting()
                ));
    }

    @PostMapping("/vehiculos")
    public Vehiculo agregarVehiculo(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.guardar(vehiculo);
    }

    @PutMapping("/vehiculos/{id}")
    public Vehiculo modificarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo) {
        return vehiculoService.actualizar(id, vehiculo);
    }

    @DeleteMapping("/vehiculos/{id}")
    public void eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminar(id);
    }
}