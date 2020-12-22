package com.example.algamoney.api.dto;

import java.math.BigDecimal;

import com.example.algamoney.api.model.Categoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoEstatisticaCategoria {

	private Categoria categoria;
	
	private BigDecimal total;

	public LancamentoEstatisticaCategoria(Categoria categoria, BigDecimal total) {		
		this.categoria = categoria;
		this.total = total;
	}	
	
}
