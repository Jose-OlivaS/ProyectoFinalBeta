package com.example.proyectofinalbackend.alquiler.dto;

import com.example.proyectofinalbackend.alquiler.model.Role;
import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Role role;
}
