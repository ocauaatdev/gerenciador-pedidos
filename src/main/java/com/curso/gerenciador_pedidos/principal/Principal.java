package com.curso.gerenciador_pedidos.principal;

import com.curso.gerenciador_pedidos.model.Categoria;
import com.curso.gerenciador_pedidos.model.ItemPedido;
import com.curso.gerenciador_pedidos.model.Pedido;
import com.curso.gerenciador_pedidos.model.Produto;
import com.curso.gerenciador_pedidos.repository.CategoriaRepository;
import com.curso.gerenciador_pedidos.repository.ItemPedidoRepository;
import com.curso.gerenciador_pedidos.repository.PedidoRepository;
import com.curso.gerenciador_pedidos.repository.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private ProdutoRepository produtoRepository;

    private CategoriaRepository categoriaRepository;

    private PedidoRepository pedidoRepository;

    private ItemPedidoRepository itemPedidoRepository;

    Scanner scanner = new Scanner(System.in);

    public Principal(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Principal() {
    }

    public void executar(){

        System.out.println("Iniciando o gerenciador de pedidos...");

        int opcao = -1;

        while (opcao != 0){

            System.out.println("Selecione uma opção:\n1. Adicionar nova categoria\n2. Adicionar novo produto\n3. Realizar novo pedido\n4. Listar categorias\n5. Listar produtos\n6. Listar pedidos\n0. Sair");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    criarCategoria();
                    break;
                case 2:
                    criarProduto();
                    break;
                case 3:
                    realizarPedido();
                    break;
                case 4:
                    exibirCategorias();
                    break;
                case 5:
                    exibirProdutos();
                    break;
                case 6:
                    exibirPedidos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

    }

    private void criarProduto(){
        Categoria categoria = null;

        System.out.println("Digite o nome do produto:");
        var nomeProduto = scanner.nextLine();

        System.out.println("Digite o preço do produto:");
        var precoProduto = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Selecione a categoria do produto:");
        exibirCategorias();
        var nomeCategoria = scanner.nextLine();

        //Buscando todas categorias
        List<Categoria> categorias = categoriaRepository.findAll();

        Optional<Categoria> categoriaOptional = categorias.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nomeCategoria.toLowerCase()))
                .findFirst();

        if (categoriaOptional.isPresent()){
            categoria = categoriaOptional.get();
        }

        Produto produto = new Produto(nomeProduto, precoProduto);
        produto.setCategoria(categoria);
        produtoRepository.save(produto);

        System.out.println("******* PRODUTO REGISTRADO COM SUCESSO *******");
    }

    private void criarCategoria(){
        System.out.println("Digite o nome da categoria:");
        String nome = scanner.nextLine();
        Categoria categoria = new Categoria(nome);
        categoriaRepository.save(categoria);

        System.out.println("******* CATEGORIA CRIADA COM SUCESSO *******");
    }

    private void realizarPedido(){
        Pedido pedido = new Pedido();


        pedido.setData(LocalDate.now());

        int seletor = 0;
        List<Produto> produtos = produtoRepository.findAll();

        while (seletor != -1){
            System.out.println("Selecione o código do produto para adicionar ao pedido ou digite '-1' para finalizar o pedido:");
            exibirProdutos();
            seletor = scanner.nextInt();

            int finalSeletor = seletor;
            Optional<Produto> produto = produtos.stream()
                    .filter(p -> p.getId().equals((long) finalSeletor))
                    .findFirst();

            if (produto.isPresent()){
                ItemPedido itemPedido = new ItemPedido();
                var produtoEncontrado = produto.get();

                System.out.println("Digite a quantidade:");
                var quantidade = scanner.nextInt();
                scanner.nextLine();

                itemPedido.setProduto(produtoEncontrado);
                itemPedido.setQuantidade(quantidade);
                itemPedido.setPrecoUnitario(produtoEncontrado.getPreco());

                pedido.adicionarItem(itemPedido);
            }
        }
        System.out.println("******* PEDIDO FINALIZADO COM SUCESSO *******");
        pedidoRepository.save(pedido);

    }

    private void exibirCategorias(){
        categoriaRepository.findAll().forEach(categoria -> {
            System.out.println("Categoria: " + categoria.getNome());
        });
    }

    private void exibirProdutos(){
        produtoRepository.findAll().forEach(produto -> {
            System.out.println("Código:" + produto.getId() + " - " + "Produto: " + produto.getNome() + " - Preço: " + produto.getPreco());
        });
    }

    private void exibirPedidos(){
        pedidoRepository.findAll().forEach(pedido -> {
            System.out.println("Pedido ID: " + pedido.getId() + " - Data: " + pedido.getData());
        });
    }
}
