package com.example.algamoney.api.service;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CidadeService {
	
	private final CidadeRepository repository;
	
	public void validarCidadeCadastrada(Long codigoEstado, String nomeCidade) {
		Cidade cidadePesquisada = this.repository.findByNome(nomeCidade);
		if (cidadePesquisada == null && !nomeCidade.trim().equals("")) {
			cidadePesquisada = new Cidade();
			cidadePesquisada.setEstado(new Estado());
			
			cidadePesquisada.setNome(nomeCidade);
			cidadePesquisada.getEstado().setCodigo(codigoEstado);
			this.repository.save(cidadePesquisada);
		}
	}

}
