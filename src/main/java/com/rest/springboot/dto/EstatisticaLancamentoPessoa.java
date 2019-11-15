package com.rest.springboot.dto;

import java.math.BigDecimal;

import com.rest.springboot.models.Pessoa;
import com.rest.springboot.models.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticaLancamentoPessoa {
	
	private TipoLancamento tipo;
	
	private Pessoa pessoa;
	
	private BigDecimal total;

}