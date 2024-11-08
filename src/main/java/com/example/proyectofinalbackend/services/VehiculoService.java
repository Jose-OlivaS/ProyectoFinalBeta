package com.example.proyectofinalbackend.services;

import com.example.proyectofinalbackend.model.Vehiculo;
import com.example.proyectofinalbackend.Repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> obtenerVehiculosDisponibles() {
        return vehiculoRepository.findByDisponible(true);
    }

    public List<Vehiculo> obtenerVehiculosPorMarca(String marca) {
        return vehiculoRepository.findAll().stream()
                .filter(v -> v.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public List<Vehiculo> buscarVehiculos(String marca, String modelo) {
        return List.of();
    }

    public List<Vehiculo> obtenerTodos() {
        return List.of();
    }

    public Vehiculo guardar(Vehiculo vehiculo) {
        return vehiculo;
    }

    public Vehiculo actualizar(Long id, Vehiculo vehiculo) {
        return vehiculo;
    }

    public void eliminar(Long id) {
    }
}