package com.rest.springboot.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Lancamento;
import com.rest.springboot.repositories.lancamento.LancamentoRepositoryQueries;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> , LancamentoRepositoryQueries {

	Lancamento findLancamentoById(Long id);
	
	public List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
	
}
