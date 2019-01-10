package com.bss.socialbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bss.socialbooks.domain.Comentario;

//Camada de Repositorio
public interface ComentariosRepository extends JpaRepository<Comentario, Long> {

}
