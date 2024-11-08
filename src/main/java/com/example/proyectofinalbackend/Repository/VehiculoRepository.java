package com.example.proyectofinalbackend.Repository;

import com.example.proyectofinalbackend.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByMarcaAndModeloAndDisponible(String marca, String modelo, boolean disponible);

    List<Vehiculo> findByDisponible(boolean b);
}
