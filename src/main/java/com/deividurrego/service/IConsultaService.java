package com.deividurrego.service;

import java.time.LocalDateTime;
import java.util.List;

import com.deividurrego.dto.ConsultaListaExamenDTO;
import com.deividurrego.dto.ConsultaResumenDTO;
import com.deividurrego.dto.FiltroConsultaDTO;
import com.deividurrego.model.Consulta;

public interface IConsultaService extends ICRUD<Consulta, Integer>{

	Consulta registrarTransaccional(ConsultaListaExamenDTO consulta) throws Exception;
	
	List<Consulta> buscar(FiltroConsultaDTO filtro);
	
	List<Consulta> buscarFecha(LocalDateTime fecha);
	
	List<ConsultaResumenDTO> listarResumen();
	
}
