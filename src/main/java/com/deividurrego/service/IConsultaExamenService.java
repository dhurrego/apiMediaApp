package com.deividurrego.service;

import java.util.List;

import com.deividurrego.model.ConsultaExamen;

public interface IConsultaExamenService {
	
	List<ConsultaExamen> listarExamenesPorConsulta(Integer idConsulta);

}
