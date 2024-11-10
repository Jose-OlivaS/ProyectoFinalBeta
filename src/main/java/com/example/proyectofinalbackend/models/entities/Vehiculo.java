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
@Table(name="vehiculo")  // Updated table name
public class Vehiculo implements Serializable {  // Updated class name

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name="matricula")
	private String matricula;

	@Column(name="marca")
	private String marca;

	@Column(name="modelo")
	private String modelo;

	@Column(name="foto")
	private String foto;

	public Vehiculo() {}

	public Long getId() {
		return id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
		Vehiculo other = (Vehiculo) obj;  // Updated class name
		return Objects.equals(id, other.id);
	}

	private static final long serialVersionUID = 1L;
}
