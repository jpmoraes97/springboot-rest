package com.rest.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min = 5, max = 70)
	private String descricao;
	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	@NotNull
	private BigDecimal valor;
	private String observacao;
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Categoria categoria;
	

}