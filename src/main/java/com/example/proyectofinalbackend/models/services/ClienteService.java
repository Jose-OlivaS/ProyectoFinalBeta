package com.example.proyectofinalbackend.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.example.proyectofinalbackend.models.entities.Cliente;

public interface ClienteService {
	
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public Cliente findOne(Long id);
	public void save(Cliente cliente);
	public void remove(Long id);
	public Long count();
	
	
	
}
