package com.example.proyectofinalbackend.services;

import com.example.proyectofinalbackend.Repository.UsuarioRepository;
import com.example.proyectofinalbackend.model.Cliente;
import com.example.proyectofinalbackend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarCliente(Usuario usuario, Cliente cliente) {
        usuario.setRol("CLIENTE");
        cliente.setUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
