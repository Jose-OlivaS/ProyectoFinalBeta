package com.example.proyectofinalbackend.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import javax.validation.constraints.NotBlank;



@Entity
@Table(name="cliente")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotBlank
@Column(name="dni")
private String dni;


@Column(name="nombre")
private String nombre;


@Column(name="apellido1")
private String apellido1;


@Column(name="apellido2")
private String apellido2;



	
	public Cliente() {}


	public Long getId() {
		return id;
	}


	public String getDni() {
		return dni;
}
public void setDni(String dni) {
	this.dni = dni;
}
public String getNombre() {
		return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getApellido1() {
		return apellido1;
}
public void setApellido1(String apellido1) {
	this.apellido1 = apellido1;
}
public String getApellido2() {
		return apellido2;
}
public void setApellido2(String apellido2) {
	this.apellido2 = apellido2;
}

	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
