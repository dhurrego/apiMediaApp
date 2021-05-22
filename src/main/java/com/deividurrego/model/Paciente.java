package com.deividurrego.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity // Representa la abstraccion de una tabla de la BD
@Table(name = "paciente")
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaciente;
	
	@NotEmpty
	@Size(min= 3, max = 70, message = "{nombres.size}")
	@Pattern(regexp = "[a-zA-Z ]+", message = "El campo nombres solo permite letras")
	@Column(name = "nombres", nullable = false, length = 70)
	private String nombres;
	
	@NotEmpty
	@Size(min= 3, max = 70, message = "{apellidos.size}")
	@Pattern(regexp = "[a-zA-Z ]+", message = "El campo apellidos solo permite letras")
	@Column(name = "apellidos", nullable = false, length = 70)
	private String apellidos;
	
	@NotEmpty
	@Size(min= 6, max = 10, message = "El campo dni debe tener mínimo 6 digitos y máximo 10")
	@Pattern(regexp = "[0-9]+", message = "El campo dni solo permite digitos")
	@Column(name = "dni", nullable = false, length = 10, unique = true)
	private String dni;
	
	@NotEmpty
	@Size(min= 8, max = 150, message = "El campo dirección debe tener mínimo 8 caracteres y máximo 150")
	@Column(name = "direccion", nullable = false, length = 150)
	private String direccion;
	
	@NotEmpty
	@Size(min= 6, max = 10, message = "El campo télefono debe tener mínimo 6 caracteres y máximo 10")
	@Pattern(regexp = "[0-9]+", message = "El campo télefono solo permite digitos")
	@Column(name = "telefono", nullable = false, length = 10)
	private String telefono;
	
	@NotEmpty
	@Size(max = 120, message = "El campo email debe tener máximo 120 caracteres")
	@Email(message = "El campo email no cumple con las características")
	@Column(name = "email", nullable = false, length = 120)
	private String email;

	public Integer getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}
	
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
