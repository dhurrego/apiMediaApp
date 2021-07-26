package com.deividurrego.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deividurrego.model.Paciente;

public interface IPacienteService extends ICRUD<Paciente, Integer> {

	Page<Paciente> listarPageable(Pageable pageable);
	
}
