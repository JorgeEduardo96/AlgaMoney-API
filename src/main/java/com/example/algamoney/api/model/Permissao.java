package com.example.algamoney.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Permissao {
	
	@Id
	@EqualsAndHashCode.Include
	private Long codigo;
	
	private String descricao;

}
