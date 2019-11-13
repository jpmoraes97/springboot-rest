package com.rest.springboot.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rest.springboot.models.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstatisticaLancamentoDia {
	
	private TipoLancamento tipo;
	
	private LocalDate dia;
	
	private BigDecimal total;
}
