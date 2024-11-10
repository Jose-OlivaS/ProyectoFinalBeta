package com.example.proyectofinalbackend.models.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.proyectofinalbackend.models.entities.Vehiculo;

public interface VehiculoService {
	public List<Vehiculo> findAll();
	public Page<Vehiculo> findAll(Pageable pageable);
	public Vehiculo findOne(Long id);
	public void save(Vehiculo vehiculo);
	public void remove(Long id);
	public Long count();
}