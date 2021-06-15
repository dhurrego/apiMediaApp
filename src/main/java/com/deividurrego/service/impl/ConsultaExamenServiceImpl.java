package com.deividurrego.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deividurrego.model.ConsultaExamen;
import com.deividurrego.repo.IConsultaExamenRepo;
import com.deividurrego.service.IConsultaExamenService;

@Service
public class ConsultaExamenServiceImpl implements IConsultaExamenService {

	@Autowired
	private IConsultaExamenRepo repo;

	@Override
	public List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsulta) {
		return repo.listarExamenesPorConsulta(idConsulta);
	}
	
}
