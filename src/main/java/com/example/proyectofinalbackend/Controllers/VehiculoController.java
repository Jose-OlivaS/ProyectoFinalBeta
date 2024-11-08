package com.example.proyectofinalbackend.Controllers;

import com.example.proyectofinalbackend.model.Vehiculo;
import com.example.proyectofinalbackend.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/disponibles")
    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return vehiculoService.obtenerVehiculosDisponibles();
    }

    @GetMapping("/marca/{marca}")
    public List<Vehiculo> obtenerVehiculosPorMarca(@PathVariable String marca) {
        return vehiculoService.obtenerVehiculosPorMarca(marca);
    }
}
