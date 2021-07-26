package com.deividurrego.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.deividurrego.exception.ModeloNotFoundException;
import com.deividurrego.model.Paciente;
import com.deividurrego.service.IPacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	private IPacienteService service;

	@GetMapping
	public ResponseEntity<List<Paciente>> listar() throws Exception{
		List<Paciente> lista = service.listar();
		return new ResponseEntity<List<Paciente>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public EntityModel<Paciente> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Paciente paciente = service.listarPorId(id);
		if(paciente == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: ".concat(id.toString()));
		}
		EntityModel<Paciente> recurso = EntityModel.of(paciente);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).listarPorId(id));
		recurso.add(link.withRel("paciente-recurso"));
		return recurso;
	}

	/*@PostMapping
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente pac) throws Exception{
		Paciente paciente = service.registrar(pac);
		return new ResponseEntity<Paciente>(paciente, HttpStatus.CREATED);
	}*/
	
	@PostMapping
	public ResponseEntity<Paciente> registrar(@Valid @RequestBody Paciente paciente) throws Exception{
		Paciente pac = service.registrar(paciente);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdPaciente()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Paciente> modificar(@Valid @RequestBody Paciente pac) throws Exception{
		Paciente paciente = service.modificar(pac);
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Paciente paciente = service.listarPorId(id);
		if(paciente == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: ".concat(id.toString()));
		}
		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Paciente>> listarPageable(Pageable pageable) throws Exception {
		Page<Paciente> pacientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<Paciente>>(pacientes, HttpStatus.OK);
	}

}
