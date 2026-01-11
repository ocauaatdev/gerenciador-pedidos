package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}
