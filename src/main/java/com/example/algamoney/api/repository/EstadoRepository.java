package com.example.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
