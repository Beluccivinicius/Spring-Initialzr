package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController // Adicionando Controller na classe
@RequestMapping("/Postagens") // declarando o end point
@CrossOrigin(origins = "*", allowedHeaders = "*") // Adicionando headers
public class PostagemController {
	
	//injetando a dependência Repository postagemRepository
	@Autowired
	private PostagemRepository postagemRepository;
	
	//Rota Get para pegar todas as postagens do banco de dados, utilizando o repositorio .finAll
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	//O metodo findById retorna uma array de informações, então se faz necessário o uso do map
	@GetMapping("/id/{id}")
	public ResponseEntity<Postagem> getOne(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());	
	}
	
	//Buscar por valor do título, utilizando o SELECT * FROM tb_postagem WHERE titulo Like 
	//"%alguma coisa%"
	@GetMapping("titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> createPost(@Valid @RequestBody Postagem post){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(post));
	}
	
	@DeleteMapping("/{id}")
	public void deletePost(@PathVariable Long id){
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if(postagem.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		postagemRepository.deleteById(id);
	}
	
}