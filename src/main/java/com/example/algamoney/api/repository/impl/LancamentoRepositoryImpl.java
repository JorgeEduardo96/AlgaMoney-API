package com.example.algamoney.api.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.model.Categoria_;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.model.Pessoa_;
import com.example.algamoney.api.model.TipoLancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import com.example.algamoney.api.repository.query.LancamentoRepositoryQuery;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<LancamentoEstatisticaPessoa> criteria = builder
				.createQuery(LancamentoEstatisticaPessoa.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaPessoa.class, root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa),
				builder.sum(root.get(Lancamento_.valor))));
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), inicio),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), fim)
				);
		
		criteria.groupBy(root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa));
		
		TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}

	
	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<LancamentoEstatisticaDia> criteria = builder
				.createQuery(LancamentoEstatisticaDia.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaDia.class, root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				builder.sum(root.get(Lancamento_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
				);
		
		criteria.groupBy(root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento));
		
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}


	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<LancamentoEstatisticaCategoria> criteria = builder
				.createQuery(LancamentoEstatisticaCategoria.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(LancamentoEstatisticaCategoria.class, root.get(Lancamento_.categoria),
				builder.sum(root.get(Lancamento_.valor))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
				);
		
		criteria.groupBy(root.get(Lancamento_.categoria));
		
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(criteria);
		
		return typedQuery.getResultList();
	}

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		// criar as restrições
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adcionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));
	}

	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(builder.construct(ResumoLancamento.class, root.get(Lancamento_.codigo),
				root.get(Lancamento_.descricao), root.get(Lancamento_.dataVencimento),
				root.get(Lancamento_.dataPagamento), root.get(Lancamento_.valor), root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome), root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adcionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(filter));

	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + filter.getDescricao().toLowerCase() + "%"));
		}

		if (filter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}

		if (filter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}

		if (!StringUtils.isEmpty(filter.getTipo())) {
			if (filter.getTipo().equalsIgnoreCase("RECEITA"))
				predicates.add(builder.equal(root.get(Lancamento_.tipo), TipoLancamento.RECEITA));
			else
				predicates.add(builder.equal(root.get(Lancamento_.tipo), TipoLancamento.DESPESA));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adcionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> lancamentosPorPessoa(Long codigoPessoa) {
		return manager.createQuery("SELECT l FROM Lancamento l WHERE l.pessoa.codigo = :codigoPessoa")
				.setParameter("codigoPessoa", codigoPessoa).getResultList();
	}

}
