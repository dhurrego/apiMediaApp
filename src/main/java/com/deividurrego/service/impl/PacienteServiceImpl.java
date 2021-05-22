package com.deividurrego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deividurrego.model.Paciente;
import com.deividurrego.repo.IGenericRepo;
import com.deividurrego.repo.IPacienteRepo;
import com.deividurrego.service.IPacienteService;

@Service
public class PacienteServiceImpl extends CRUDImpl<Paciente, Integer> implements IPacienteService {

	@Autowired
	private IPacienteRepo repo;

	@Override
	public IGenericRepo<Paciente, Integer> getRepo() {
		return repo;
	}

}
