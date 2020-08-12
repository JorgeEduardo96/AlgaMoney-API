package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(Lancamento lancamento) {
		this.validarPessoa(lancamento);
		return this.repository.save(lancamento);
	}
	
	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = this.buscarLancamento(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
		
		return this.repository.save(lancamentoSalvo);
	}

	private void validarPessoa(Lancamento lancamento) {
		Optional<Pessoa> pessoa = null;

		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		}

		if (!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}

	}

	private Lancamento buscarLancamento(Long codigo) {
		Optional<Lancamento> lancamento = this.repository.findById(codigo);

		if (!lancamento.isPresent()) {
			throw new IllegalArgumentException();
		}

		return lancamento.get();
	}

}
