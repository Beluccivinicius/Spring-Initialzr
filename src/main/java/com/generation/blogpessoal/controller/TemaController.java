package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Adicionando headers
public class TemaController {
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAllTemas(){
		return ResponseEntity.ok(temaRepository.findAll()); 
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Tema> getaOneTema(@PathVariable Long id){
		return temaRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByTema(@PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PutMapping
	public ResponseEntity<Tema> putPostagem(@Valid @RequestBody Tema tema){
		
		if(temaRepository.findById(tema.getId()).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não identificado!!", null);
		}
		
		return ResponseEntity.ok(temaRepository.save(tema));
		
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema){
		if(!temaRepository.existsById(tema.getId())) {
			return ResponseEntity.ok(temaRepository.save(tema));
		}
		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema já existe!", null);
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteTema(@PathVariable Long id) {
		
		var isValidId = temaRepository.findById(id);
		
		if(isValidId.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		temaRepository.deleteById(id);
		
	}
}
