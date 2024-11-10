package com.example.proyectofinalbackend.models.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Entity
@Table(name="alquiler")
public class Alquiler implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fechainicio")
private LocalDate fechaInicio;


@DateTimeFormat(pattern = "yyyy-MM-dd")
@Column(name="fechafin")
private LocalDate fechaFin;


@Column(name="matricula")
private String matricula;


@Column(name="dni")
private String dni;



	
	public Alquiler() {}


	public Long getId() {
		return id;
	}


	public LocalDate getFechaInicio() {
		return fechaInicio;
}
public void setFechaInicio(LocalDate fechaInicio) {
	this.fechaInicio = fechaInicio;
}
public LocalDate getFechaFin() {
		return fechaFin;
}
public void setFechaFin(LocalDate fechaFin) {
	this.fechaFin = fechaFin;
}
public String getMatricula() {
		return matricula;
}
public void setMatricula(String matricula) {
	this.matricula = matricula;
}
public String getDni() {
		return dni;
}
public void setDni(String dni) {
	this.dni = dni;
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
		Alquiler other = (Alquiler) obj;
		return Objects.equals(id, other.id);
	}


	private static final long serialVersionUID = 1L;
	
}
