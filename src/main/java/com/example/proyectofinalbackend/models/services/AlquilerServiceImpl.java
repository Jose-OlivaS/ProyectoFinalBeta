package com.example.proyectofinalbackend.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.example.proyectofinalbackend.models.dao.AlquilerDAO;
import com.example.proyectofinalbackend.models.entities.Alquiler;


@Service
public class AlquilerServiceImpl implements AlquilerService {

	private final AlquilerDAO alquilerDAO;
	
	
	
	public AlquilerServiceImpl(
			
			AlquilerDAO alquilerDAO
			) {
		
		this.alquilerDAO = alquilerDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Alquiler> findAll() {
		return (List<Alquiler>) alquilerDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Alquiler> findAll(Pageable pageable) {
		return alquilerDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Alquiler findOne(Long id) {
		return alquilerDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Alquiler alquiler) {
		alquilerDAO.save(alquiler);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		alquilerDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return alquilerDAO.count();
	}
	
	
	
	
}
