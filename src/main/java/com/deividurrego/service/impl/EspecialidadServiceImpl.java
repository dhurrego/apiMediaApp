package com.deividurrego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deividurrego.model.Especialidad;
import com.deividurrego.repo.IEspecialidadRepo;
import com.deividurrego.repo.IGenericRepo;
import com.deividurrego.service.IEspecialidadService;

@Service
public class EspecialidadServiceImpl extends CRUDImpl<Especialidad, Integer> implements IEspecialidadService {
	
	@Autowired
	private IEspecialidadRepo repo;

	@Override
	public IGenericRepo<Especialidad, Integer> getRepo() {
		return repo;
	}
	
}
