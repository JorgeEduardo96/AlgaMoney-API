package com.example.algamoney.api.dto;

import lombok.Data;

public class Anexo {

	private String nome;

	private String url;

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return url;
	}

	public Anexo(String nome, String url) {
		this.nome = nome;
		this.url = url;
	}

}
