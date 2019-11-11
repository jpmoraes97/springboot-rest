package com.rest.springboot.repositories.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rest.springboot.models.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoResumo {
	
	private Long id;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String pessoa;
	private String categoria;

}
