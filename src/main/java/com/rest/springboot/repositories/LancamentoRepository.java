package com.rest.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	Lancamento findLancamentoById(Long id);
	
}
