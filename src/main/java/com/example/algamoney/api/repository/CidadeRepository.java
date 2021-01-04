package com.example.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Cidade;
import com.example.algamoney.api.repository.query.CidadeRepositoryQuery;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery {		

	List<Cidade> findByEstadoCodigo(Long estadoCodigo);
	Cidade findByNome(String nome);
	
}
