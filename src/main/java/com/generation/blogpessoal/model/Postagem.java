package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // classe vai se tornar uma tabela no meu banco de dados
@Table(name="tb_postagens")// nomeando a tabela no banco de dados como tb_postagens
public class Postagem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id; 
	
	@NotBlank(message = "O atributo TITULO deve ser preenchido")
	@Size(min = 5, max = 100, message = "O atributo TITULO deve ter no minimo 5 caractere e no máximo 100 caractere")
	private String titulo;
	
	@NotBlank(message = "O atributo TEXTO deve ser preenchido")
	@Size(min = 10, max = 1000, message = "o atributo TEXTO deve ter no minimo 10 caractere e no máximo 1000")
	private String texto;
	
	@UpdateTimestamp
	private LocalDateTime data;
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}


}
