package com.rest.springboot.dto;

import java.math.BigDecimal;

import com.rest.springboot.models.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstatisticaLancamentoCategoria {

	private Categoria categoria;
	
	private BigDecimal total;
	
}
