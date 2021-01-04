package com.example.algamoney.api.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.algamoney.api.repository.query.CidadeRepositoryQuery;

public class CidadeRepositoryImpl implements CidadeRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

}
