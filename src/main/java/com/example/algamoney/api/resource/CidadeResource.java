package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;
import com.example.algamoney.api.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeResource {

	private final CidadeRepository repository;

	private final CidadeService service;

	@GetMapping()
	private List<Cidade> pesquisar(@RequestParam Long codigoEstado, @RequestParam(required = false) String nomeCidade) {
		if (!StringUtils.isEmpty(nomeCidade))
			this.service.validarCidadeCadastrada(codigoEstado, nomeCidade);

		return this.repository.findByEstadoCodigo(codigoEstado);
	}		
}
