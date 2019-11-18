package com.rest.springboot.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "estado")
public class Estado {

	@Id
	@EqualsAndHashCode.Include
	private Long id;
	private String nome;	
}