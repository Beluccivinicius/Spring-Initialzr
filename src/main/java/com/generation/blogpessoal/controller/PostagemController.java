package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController // Adicionando Controller na classe
@RequestMapping("/Postagem") // declarando o end point
@CrossOrigin(origins = "*", allowedHeaders = "*") // Adicionando headers
public class PostagemController {
	
	//injetando a dependência Repository postagemRepository
	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		
		return ResponseEntity.ok(postagemRepository.findAll());
		
	}
}
