package com.example.proyectofinalbackend.Repository;

import com.example.proyectofinalbackend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
