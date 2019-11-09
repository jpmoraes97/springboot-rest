package com.rest.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Pessoa findPessoaById(Long id);

}
