package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping()
	private List<Cidade> pesquisar(@RequestParam Long codigoEstado) {
		return this.repository.findByEstadoCodigo(codigoEstado);
	}

	@GetMapping("/porNome")
	private Cidade buscarPeloNome(@RequestParam String nomeCidade, @RequestParam Long codigoEstado) {
		Cidade cidadePesquisada = this.repository.findByNome(nomeCidade);

		if (cidadePesquisada == null) {
			cidadePesquisada = new Cidade();
			cidadePesquisada.setNome(nomeCidade);
			cidadePesquisada.getEstado().setCodigo(codigoEstado);
			cidadePesquisada = this.repository.save(cidadePesquisada);
		}

		return cidadePesquisada;
	}

	@PostMapping
	private ResponseEntity<Cidade> criar(@Valid @RequestBody Cidade cidade, HttpServletResponse response) {
		cidade = this.repository.save(cidade);

		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, cidade.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
	}

}
