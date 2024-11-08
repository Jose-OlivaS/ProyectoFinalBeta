package com.example.proyectofinalbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String rol;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Cliente cliente;
}