package com.rest.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.springboot.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
