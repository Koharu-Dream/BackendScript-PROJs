package com.lab.jpa.sisbiblioteca.repository;

import com.lab.jpa.sisbiblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    // busca autor junto com os livros, evita erro de lazy loading
    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.livros WHERE a.id = :id")
    Optional<Autor> buscarComLivros(@Param("id") Long id);
}