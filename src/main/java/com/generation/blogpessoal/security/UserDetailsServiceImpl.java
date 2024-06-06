package com.generation.blogpessoal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//UserDetailsServiceImpl : Objetivo da classe: Verificar se o usuário 
	//existe no banco de dados , faz a validação quando o usuário tentar 
	//cadastrar um email que já existe por exemplo
	
	@Autowired
	private UsuarioRepository usuarioRespository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRespository.findByUsuario(username);
		
		if(usuario.isPresent())
			return new UserDetailsImpl(usuario.get());
					
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);


	}
}