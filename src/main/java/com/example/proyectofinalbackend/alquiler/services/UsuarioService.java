package com.example.proyectofinalbackend.alquiler.services;

import com.example.proyectofinalbackend.alquiler.dto.UsuarioDTO;
import com.example.proyectofinalbackend.alquiler.model.Usuario;
import com.example.proyectofinalbackend.alquiler.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createUser(UsuarioDTO userDTO) {
        Usuario user = new Usuario();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }

    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
