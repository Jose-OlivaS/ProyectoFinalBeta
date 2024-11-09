package com.example.proyectofinalbackend.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String fullName;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;  // Aquí usas el Role enum definido en tu paquete

    @OneToMany(mappedBy = "user")
    private Set<Reserva> reservations;

}
