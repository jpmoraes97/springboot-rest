package com.rest.springboot.repositories.lancamento;

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

import com.rest.springboot.dto.EstatisticaLancamentoCategoria;
import com.rest.springboot.dto.EstatisticaLancamentoDia;
import com.rest.springboot.dto.EstatisticaLancamentoPessoa;
import com.rest.springboot.models.Lancamento;
import com.rest.springboot.repositories.filter.LancamentoFilter;
import com.rest.springboot.repositories.projection.LancamentoResumo;

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
	
	@Override
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumo> criteriaQuery = criteriaBuilder.createQuery(LancamentoResumo.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(criteriaBuilder.construct(LancamentoResumo.class,
				root.get("id"), root.get("descricao"), root.get("dataVencimento"),
				root.get("dataPagamento"), root.get("valor"), root.get("tipo"), 
				root.get("pessoa").get("nome"), root.get("categoria").get("nome")));
		
		//Criar as restricoes
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<LancamentoResumo> typedQuery = manager.createQuery(criteriaQuery);
		
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
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> typedQuery, Pageable pageable) {
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

	@Override
	public List<EstatisticaLancamentoCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EstatisticaLancamentoCategoria> criteriaQuery = builder.createQuery(EstatisticaLancamentoCategoria.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(builder.construct(EstatisticaLancamentoCategoria.class,
				root.get("categoria"), builder.sum(root.get("valor"))));
		
		LocalDate primeiroDiaMes = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDiaMes = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDiaMes),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDiaMes)
				);
		
		criteriaQuery.groupBy(root.get("categoria"));
		
		TypedQuery<EstatisticaLancamentoCategoria> typedQuery = manager.createQuery(criteriaQuery);
	
		return typedQuery.getResultList();
	}

	@Override
	public List<EstatisticaLancamentoDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EstatisticaLancamentoDia> criteriaQuery = builder.createQuery(EstatisticaLancamentoDia.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(builder.construct(EstatisticaLancamentoDia.class,
				root.get("tipo"), root.get("dataVencimento"), builder.sum(root.get("valor"))));
		
		LocalDate primeiroDiaMes = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDiaMes = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteriaQuery.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDiaMes),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDiaMes)
				);
		
		criteriaQuery.groupBy(root.get("tipo"), root.get("dataVencimento"));
		
		TypedQuery<EstatisticaLancamentoDia> typedQuery = manager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<EstatisticaLancamentoPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<EstatisticaLancamentoPessoa> criteriaQuery = builder.createQuery(EstatisticaLancamentoPessoa.class);
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery.select(builder.construct(EstatisticaLancamentoPessoa.class,
				root.get("tipo"), root.get("pessoa"), builder.sum(root.get("valor"))));
		
		criteriaQuery.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), inicio),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), fim)
				);
		
		criteriaQuery.groupBy(root.get("tipo"), root.get("pessoa"));
		
		TypedQuery<EstatisticaLancamentoPessoa> typedQuery = manager.createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
		
}