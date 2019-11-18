package com.rest.springboot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstadoId(Long estadoId);
	
}
