package com.curso.gerenciador_pedidos;

import com.curso.gerenciador_pedidos.principal.Principal;
import com.curso.gerenciador_pedidos.repository.CategoriaRepository;
import com.curso.gerenciador_pedidos.repository.PedidoRepository;
import com.curso.gerenciador_pedidos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorPedidosApplication implements CommandLineRunner {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorPedidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(produtoRepository, categoriaRepository, pedidoRepository);
		principal.executar();
	}
}
