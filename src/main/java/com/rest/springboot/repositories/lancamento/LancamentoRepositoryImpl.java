package com.rest.springboot.repositories.lancamento;

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

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.repositories.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		
		//Criar as restricoes
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		
		TypedQuery<Lancamento> typedQuery = manager.createQuery(criteriaQuery);
		
		adicionarRestricoesDePaginacao(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(lancamentoFilter));	
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(criteriaBuilder.like(
					criteriaBuilder.lower(root.get("descricao")), 
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(totalRegistrosPorPagina);	
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(criteriaBuilder.count(root));
		
		return manager.createQuery(criteriaQuery).getSingleResult();
	}
		
}