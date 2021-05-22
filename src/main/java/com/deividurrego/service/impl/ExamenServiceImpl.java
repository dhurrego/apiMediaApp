package com.deividurrego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deividurrego.model.Examen;
import com.deividurrego.repo.IExamenRepo;
import com.deividurrego.repo.IGenericRepo;
import com.deividurrego.service.IExamenService;

@Service
public class ExamenServiceImpl extends CRUDImpl<Examen, Integer> implements IExamenService {
	
	@Autowired
	private IExamenRepo repo;

	@Override
	public IGenericRepo<Examen, Integer> getRepo() {
		return repo;
	}
	
}
