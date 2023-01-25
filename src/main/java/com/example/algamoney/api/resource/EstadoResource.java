package com.example.algamoney.api.resource;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoResource {

	private final EstadoRepository repository;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Estado> listar() {
		return this.repository.findAll();
	}
	
}
