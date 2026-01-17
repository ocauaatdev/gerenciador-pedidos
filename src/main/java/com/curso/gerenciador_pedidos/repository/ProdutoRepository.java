package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.Categoria;
import com.curso.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeContainingIgnoreCase(String nomeProduto);

    Optional<Produto> findByNome(String nomeProduto);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByPrecoGreaterThanEqual(double minValor);

    List<Produto> findByPrecoLessThanEqual(double maxValor);

    long countByCategoriaNome(String categoriaNome);
}
