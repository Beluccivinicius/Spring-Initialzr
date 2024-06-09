package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		
		usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", ""));
		
	}
	
	@Test
	@DisplayName("Cadastrar usuario")
	public void cadastrarUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"vinicius", "vi@gmail.com", "vinivini", ""));

		ResponseEntity<Usuario> responseEntity = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}
	
	
	@Test
	@DisplayName("Não permissão de dois usuarios iguais")
	public void semRepeticaoUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Luciane Rocha", "lu@gmail.com", "lululu", ""));
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Luciane Rocha", "lu@gmail.com", "lululu", ""));
		
		ResponseEntity<Usuario> responseEntity = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	@Test
    @DisplayName("Atualizar usuario")
    public void atualizaUsuario() {
        Optional<Usuario> usuarioCadastrado = usuarioService
                .cadastrarUsuario(new Usuario(0L, "Erik", "erik@email.com", "123456789", ""));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Erik Faccipieri", "erik@email.com",
                "123456789", "");

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }
	
	@Test
	@DisplayName("Procurar todos os usuarios")
	public void listarUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Jorge Eduardo", "jojo@gmail.com", "jorjorjor", ""));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Luciane Rocha", "lu@gmail.com", "lululu", ""));
		
		ResponseEntity<String> corpoResposta = testRestTemplate
				.withBasicAuth("root@root.com", "rootroot")
				.exchange("/usuarios/all", HttpMethod.GET,
						null, String.class);
		
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	
	
}
