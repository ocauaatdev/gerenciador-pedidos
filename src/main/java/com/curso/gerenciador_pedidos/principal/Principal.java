package com.curso.gerenciador_pedidos.principal;

import com.curso.gerenciador_pedidos.model.Categoria;
import com.curso.gerenciador_pedidos.model.Pedido;
import com.curso.gerenciador_pedidos.model.Produto;
import com.curso.gerenciador_pedidos.repository.CategoriaRepository;
import com.curso.gerenciador_pedidos.repository.PedidoRepository;
import com.curso.gerenciador_pedidos.repository.ProdutoRepository;

import java.time.LocalDate;

public class Principal {

    private ProdutoRepository produtoRepository;

    private CategoriaRepository categoriaRepository;

    private PedidoRepository pedidoRepository;

    public Principal(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Principal() {
    }

    public void executar(){
        Categoria categoria = new Categoria("Eletronicos");
        categoriaRepository.save(categoria);

        Produto produto = new Produto("Pc Gamer", 3000.00);
        produtoRepository.save(produto);

        Pedido pedido = new Pedido(LocalDate.now());
        pedidoRepository.save(pedido);

        exibirCategorias();
        exibirProdutos();
        exibirPedidos();
    }

    private void exibirCategorias(){
        categoriaRepository.findAll().forEach(categoria -> {
            System.out.println("Categoria: " + categoria.getNome());
        });
    }

    private void exibirProdutos(){
        produtoRepository.findAll().forEach(produto -> {
            System.out.println("Produto: " + produto.getNome() + " - Preço: " + produto.getPreco());
        });
    }

    private void exibirPedidos(){
        pedidoRepository.findAll().forEach(pedido -> {
            System.out.println("Pedido ID: " + pedido.getId() + " - Data: " + pedido.getData());
        });
    }
}
