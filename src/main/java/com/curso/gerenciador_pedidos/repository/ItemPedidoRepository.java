package com.curso.gerenciador_pedidos.repository;

import com.curso.gerenciador_pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
