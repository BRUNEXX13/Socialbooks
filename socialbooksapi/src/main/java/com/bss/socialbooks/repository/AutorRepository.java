package com.bss.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bss.socialbooks.domain.Autor;

//Camada de Repositorio
public interface AutorRepository extends JpaRepository<Autor, Long> {

}
