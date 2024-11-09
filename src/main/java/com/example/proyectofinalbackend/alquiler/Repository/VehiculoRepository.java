package com.example.proyectofinalbackend.alquiler.Repository;


import com.example.proyectofinalbackend.alquiler.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByAvailableTrue();
    List<Vehiculo> findByBrandAndModel(String brand, String model);

    List<Vehiculo> findByBrand(String brand);
}