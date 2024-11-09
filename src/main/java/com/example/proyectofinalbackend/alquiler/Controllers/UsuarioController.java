package com.example.proyectofinalbackend.alquiler.Controllers;

import com.example.proyectofinalbackend.alquiler.dto.UsuarioDTO;
import com.example.proyectofinalbackend.alquiler.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {
    @Autowired
    private UsuarioService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }
}
