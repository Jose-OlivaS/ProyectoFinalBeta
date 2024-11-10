package com.example.proyectofinalbackend.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.example.proyectofinalbackend.models.dao.ClienteDAO;
import com.example.proyectofinalbackend.models.entities.Cliente;


@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteDAO clienteDAO;
	
	
	
	public ClienteServiceImpl(
			
			ClienteDAO clienteDAO
			) {
		
		this.clienteDAO = clienteDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Cliente findOne(Long id) {
		return clienteDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		clienteDAO.save(cliente);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		clienteDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return clienteDAO.count();
	}
	
	
	
	
}
