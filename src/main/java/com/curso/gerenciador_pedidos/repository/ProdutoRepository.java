package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.Categoria;
import com.curso.gerenciador_pedidos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    //Usando derived query
    Optional<Produto> findByNomeContainingIgnoreCase(String nomeProduto);

    Optional<Produto> findByNome(String nomeProduto);

    List<Produto> findByCategoria(Categoria categoria);

    List<Produto> findByPrecoGreaterThanEqual(double minValor);

    List<Produto> findByPrecoLessThanEqual(double maxValor);

    long countByCategoriaNome(String categoriaNome);

    //Usando JPQL
    @Query("SELECT p FROM Produto p WHERE p.preco >= :minValor")
    List<Produto> produtosPrecoMaior(int minValor);

    @Query("SELECT p FROM Produto p ORDER BY p.preco")
    List<Produto> buscarProdutosPrecoCrescente();

    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
    List<Produto> buscarProdutosPrecoDecrescente();

    @Query("SELECT AVG(p.preco) FROM Produto p")
    Double consultaMediaPrecosProdutos();

    @Query("SELECT COUNT(*) FROM Produto p JOIN p.categoria c WHERE LOWER(c.nome) = LOWER(:categoria)")
    Integer contarProdutosCategoria(String categoria);

    @Query("SELECT MAX(p.preco) FROM Produto p JOIN p.categoria c WHERE LOWER(c.nome) = LOWER(:categoria)")
    Double consultarPrecoMaxCategoria(String categoria);

    @Query("SELECT p FROM Produto p JOIN p.categoria c WHERE LOWER(p.nome) LIKE LOWER(CONCAT ('%', :resposta, '%')) OR  LOWER(c.nome) LIKE LOWER(CONCAT ('%', :resposta, '%'))")
    List<Produto> buscarProdutosNomeOuCategoria(String resposta);
}
