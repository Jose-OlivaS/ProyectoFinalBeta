package com.example.proyectofinalbackend.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyectofinalbackend.models.dao.VehiculoDAO;  // Cambiado a VehiculoDAO
import com.example.proyectofinalbackend.models.entities.Vehiculo;  // Cambiado a Vehiculo


@Service
public class VehiculoServiceImpl implements VehiculoService {  // Cambiado a VehiculoService

	private final VehiculoDAO vehiculoDAO;  // Cambiado a VehiculoDAO

	public VehiculoServiceImpl(
			VehiculoDAO vehiculoDAO  // Cambiado a VehiculoDAO
	) {
		this.vehiculoDAO = vehiculoDAO;  // Cambiado a VehiculoDAO
	}

	@Transactional(readOnly = true)
	@Override
	public List<Vehiculo> findAll() {  // Cambiado a Vehiculo
		return (List<Vehiculo>) vehiculoDAO.findAll();  // Cambiado a VehiculoDAO
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Vehiculo> findAll(Pageable pageable) {  // Cambiado a Vehiculo
		return vehiculoDAO.findAll(pageable);  // Cambiado a VehiculoDAO
	}

	@Transactional(readOnly = true)
	@Override
	public Vehiculo findOne(Long id) {  // Cambiado a Vehiculo
		return vehiculoDAO.findById(id).orElse(null);  // Cambiado a VehiculoDAO
	}

	@Transactional
	@Override
	public void save(Vehiculo vehiculo) {  // Cambiado a Vehiculo
		vehiculoDAO.save(vehiculo);  // Cambiado a VehiculoDAO
	}

	@Transactional
	@Override
	public void remove(Long id) {  // Cambiado a Vehiculo
		vehiculoDAO.deleteById(id);  // Cambiado a VehiculoDAO
	}

	@Transactional(readOnly = true)
	@Override
	public Long count() {  // Cambiado a Vehiculo
		return vehiculoDAO.count();  // Cambiado a VehiculoDAO
	}

}
