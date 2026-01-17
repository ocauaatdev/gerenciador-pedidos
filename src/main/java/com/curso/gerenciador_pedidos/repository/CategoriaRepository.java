package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNomeContainingIgnoreCase(String nomeCategoria);

    Object countByNome(String categoria);
}
