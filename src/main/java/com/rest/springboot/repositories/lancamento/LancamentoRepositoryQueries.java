package com.rest.springboot.repositories.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rest.springboot.dto.EstatisticaLancamentoCategoria;
import com.rest.springboot.dto.EstatisticaLancamentoDia;
import com.rest.springboot.dto.EstatisticaLancamentoPessoa;
import com.rest.springboot.models.Lancamento;
import com.rest.springboot.repositories.filter.LancamentoFilter;
import com.rest.springboot.repositories.projection.LancamentoResumo;

public interface LancamentoRepositoryQueries {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
	public List<EstatisticaLancamentoCategoria> porCategoria(LocalDate mesReferencia);
	
	public List<EstatisticaLancamentoDia> porDia(LocalDate mesReferencia);
	
	public List<EstatisticaLancamentoPessoa> porPessoa(LocalDate inicio, LocalDate fim);

}
