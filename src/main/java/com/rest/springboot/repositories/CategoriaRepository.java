package com.rest.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}