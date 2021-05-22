package com.deividurrego.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deividurrego.model.Medico;
import com.deividurrego.repo.IGenericRepo;
import com.deividurrego.repo.IMedicoRepo;
import com.deividurrego.service.IMedicoService;

@Service
public class MedicoServiceImpl extends CRUDImpl<Medico, Integer> implements IMedicoService {
	
	@Autowired
	private IMedicoRepo repo;

	@Override
	public IGenericRepo<Medico, Integer> getRepo() {
		return repo;
	}
	
}
