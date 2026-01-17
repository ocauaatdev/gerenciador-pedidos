package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByDataNull();

    List<Pedido> findByDataNotNull();
}
