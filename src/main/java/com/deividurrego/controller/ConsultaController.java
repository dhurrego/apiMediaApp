package com.deividurrego.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deividurrego.dto.ConsultaListaExamenDTO;
import com.deividurrego.dto.ConsultaResumenDTO;
import com.deividurrego.dto.FiltroConsultaDTO;
import com.deividurrego.exception.ModeloNotFoundException;
import com.deividurrego.model.Archivo;
import com.deividurrego.model.Consulta;
import com.deividurrego.service.IArchivoService;
import com.deividurrego.service.IConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

	@Autowired
	private IConsultaService service;
	
	@Autowired
	private IArchivoService archivoService;

	@GetMapping
	public ResponseEntity<List<Consulta>> listar() throws Exception {
		List<Consulta> lista = service.listar();
		return new ResponseEntity<List<Consulta>>(lista, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public EntityModel<Consulta> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Consulta consulta = service.listarPorId(id);
		if (consulta == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: ".concat(id.toString()));
		}
		EntityModel<Consulta> recurso = EntityModel.of(consulta);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(link.withRel("consulta-recurso"));
		return recurso;
	}

	/*
	 * @PostMapping public ResponseEntity<Consulta>
	 * registrar(@Valid @RequestBody Consulta con) throws Exception{
	 * Consulta consulta = service.registrar(con); return new
	 * ResponseEntity<Consulta>(consulta, HttpStatus.CREATED); }
	 */

	@PostMapping
	public ResponseEntity<Consulta> registrar(@Valid @RequestBody ConsultaListaExamenDTO consulta) throws Exception {
		Consulta con = service.registrarTransaccional(consulta);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(con.getIdConsulta()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Consulta> modificar(@Valid @RequestBody Consulta con) throws Exception {
		Consulta consulta = service.modificar(con);
		return new ResponseEntity<Consulta>(consulta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Consulta consulta = service.listarPorId(id);
		if (consulta == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: ".concat(id.toString()));
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<List<Consulta>> buscarFecha(@RequestParam(value = "fecha") String fecha) throws Exception {
		List<Consulta> consultas = new ArrayList<>();
		consultas = service.buscarFecha(LocalDateTime.parse(fecha));
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@PostMapping("/buscar/otros")
	public ResponseEntity<List<Consulta>> buscarOtro(@RequestBody FiltroConsultaDTO filtro) throws Exception {
		List<Consulta> consultas = new ArrayList<>();
		consultas = service.buscar(filtro);
		return new ResponseEntity<List<Consulta>>(consultas, HttpStatus.OK);
	}
	
	@GetMapping("/listarResumen")
	public ResponseEntity<List<ConsultaResumenDTO>> listarResumen() throws Exception {
		List<ConsultaResumenDTO> consultas = new ArrayList<>();
		consultas = service.listarResumen();
		return new ResponseEntity<List<ConsultaResumenDTO>>(consultas, HttpStatus.OK);
	}
	
	@GetMapping(value = "/generarReporte", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> generarReporte() throws Exception {
		byte[] data = null;
		data = service.generarReporte();
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}
	
	@PostMapping(value = "/guardarArchivo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Integer> guardarArchivo(@RequestParam("adjunto") MultipartFile file) throws IOException {
		int rpta = 0;
		
		Archivo ar = new Archivo();
		
		ar.setFiletype(file.getContentType());
		ar.setFilename(file.getOriginalFilename());
		ar.setValue(file.getBytes());
		
		rpta = archivoService.guardar(ar);
		
		return new ResponseEntity<Integer>(rpta, HttpStatus.OK);
	}
	
	@GetMapping(value = "/leerArchivo/{idArchivo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> leerArchivo(@PathVariable("idArchivo") Integer idArchivo) throws Exception {
		
		byte[] arr = archivoService.leerArchivo(idArchivo);
		
		return new ResponseEntity<byte[]>(arr, HttpStatus.OK);
	}

}
