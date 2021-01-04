package com.example.algamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired 
	private CidadeRepository repository;
	
	public void validarCidadeCadastrada(Long codigoEstado, String nomeCidade) {
		Cidade cidadePesquisada = this.repository.findByNome(nomeCidade);
		if (cidadePesquisada == null && !nomeCidade.trim().equals("")) {
			cidadePesquisada = new Cidade();
			cidadePesquisada.setEstado(new Estado());
			
			cidadePesquisada.setNome(nomeCidade);
			cidadePesquisada.getEstado().setCodigo(codigoEstado);
			cidadePesquisada = this.repository.save(cidadePesquisada);
		}
	}

}
