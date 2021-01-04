package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;
import com.example.algamoney.api.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository repository;

	
	@Autowired
	private CidadeService service;

	@GetMapping()
	private List<Cidade> pesquisar(@RequestParam Long codigoEstado, @RequestParam String nomeCidade) {		
		this.service.validarCidadeCadastrada(codigoEstado, nomeCidade);
		
		return this.repository.findByEstadoCodigo(codigoEstado);
	}		
}
