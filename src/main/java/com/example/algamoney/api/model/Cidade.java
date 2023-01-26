package com.example.algamoney.api.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cidade")
@Data
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "codigo_estado", referencedColumnName = "codigo")
	private Estado estado;

}