package com.deividurrego.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deividurrego.dto.ConsultaListaExamenDTO;
import com.deividurrego.dto.ConsultaResumenDTO;
import com.deividurrego.dto.FiltroConsultaDTO;
import com.deividurrego.model.Consulta;
import com.deividurrego.repo.IConsultaExamenRepo;
import com.deividurrego.repo.IConsultaRepo;
import com.deividurrego.repo.IGenericRepo;
import com.deividurrego.service.IConsultaService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ConsultaServiceImpl extends CRUDImpl<Consulta, Integer> implements IConsultaService {

	@Autowired
	private IConsultaRepo repo;
	
	@Autowired
	private IConsultaExamenRepo ceRepo;

	@Override
	public IGenericRepo<Consulta, Integer> getRepo() {
		return repo;
	}

	@Transactional
	@Override
	public Consulta registrarTransaccional(ConsultaListaExamenDTO dto) throws Exception {
		
		dto.getConsulta().getDetalleConsulta().forEach(det -> det.setConsulta(dto.getConsulta()));
		
		repo.save(dto.getConsulta());
		
		dto.getListaExamenes().forEach( ex -> ceRepo.registrar(dto.getConsulta().getIdConsulta(), ex.getIdExamen()));
		
		return dto.getConsulta();
		
	}

	@Override
	public List<Consulta> buscar(FiltroConsultaDTO filtro) {
		return repo.buscar(filtro.getDni(), filtro.getNombreCompleto());
	}

	@Override
	public List<Consulta> buscarFecha(LocalDateTime fecha) {
		return repo.buscarFecha(fecha, fecha.plusDays(1).minusSeconds(1));
	}

	@Override
	public List<ConsultaResumenDTO> listarResumen() {
		List<ConsultaResumenDTO> consultas = new ArrayList<>();
		repo.listarResumen().forEach( x -> {
			ConsultaResumenDTO cr = new ConsultaResumenDTO();
			cr.setCantidad(Integer.parseInt(String.valueOf(x[0])));
			cr.setFecha(String.valueOf(x[1]));
			consultas.add(cr);
		});
		return consultas;
	}

	@Override
	public byte[] generarReporte() {
		byte[] data = null;
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("txt_titulo", "Resumen consultas por día");
		
		try {
			File file = new ClassPathResource("reports/consultas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), parametros, new JRBeanCollectionDataSource(this.listarResumen()));
			data = JasperExportManager.exportReportToPdf(print);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}

}
