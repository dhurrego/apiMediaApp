package com.deividurrego.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.deividurrego.model.Consulta;
import com.deividurrego.model.Examen;

public class ConsultaListaExamenDTO {

	@NotNull
	private Consulta consulta;

	@NotNull
	private List<Examen> listaExamenes;

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<Examen> getListaExamenes() {
		return listaExamenes;
	}

	public void setListaExamenes(List<Examen> listaExamenes) {
		this.listaExamenes = listaExamenes;
	}

}
