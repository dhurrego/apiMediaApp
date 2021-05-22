package com.deividurrego.service;

import com.deividurrego.dto.ConsultaListaExamenDTO;
import com.deividurrego.model.Consulta;

public interface IConsultaService extends ICRUD<Consulta, Integer>{

	Consulta registrarTransaccional(ConsultaListaExamenDTO consulta);
	
}
