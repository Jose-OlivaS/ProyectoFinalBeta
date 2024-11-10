package com.example.proyectofinalbackend.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.proyectofinalbackend.models.entities.Vehiculo;  // Updated import

public interface VehiculoDAO extends PagingAndSortingRepository<Vehiculo, Long> {

}
