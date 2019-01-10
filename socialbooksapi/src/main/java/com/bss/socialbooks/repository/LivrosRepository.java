package com.bss.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bss.socialbooks.domain.Livro;

//Camada de Repositorio
public interface LivrosRepository extends JpaRepository<Livro, Long> {



}
