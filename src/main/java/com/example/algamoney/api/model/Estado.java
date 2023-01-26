package com.example.algamoney.api.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
@Data
public class Estado {
	
	@Id
	private Long codigo;
	
	private String nome;

}