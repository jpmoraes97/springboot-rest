package com.rest.springboot.repositories.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.repositories.filter.LancamentoFilter;
import com.rest.springboot.repositories.projection.LancamentoResumo;

public interface LancamentoRepositoryQueries {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
