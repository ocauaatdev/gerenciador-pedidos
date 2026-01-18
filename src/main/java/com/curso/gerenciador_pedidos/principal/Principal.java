package com.curso.gerenciador_pedidos.principal;

import com.curso.gerenciador_pedidos.model.*;
import com.curso.gerenciador_pedidos.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private ProdutoRepository produtoRepository;

    private CategoriaRepository categoriaRepository;

    private PedidoRepository pedidoRepository;

    private ItemPedidoRepository itemPedidoRepository;

    private FornecedorRepository fornecedorRepository;

    Scanner scanner = new Scanner(System.in);

    public Principal(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository, FornecedorRepository fornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public Principal() {
    }

    public void executar(){

        System.out.println("Iniciando o gerenciador de pedidos...");

//        Fornecedor fornecedor = new Fornecedor();
//        fornecedor.setNome("Fornecedor Padrão");
//        fornecedorRepository.save(fornecedor);

        int opcao = -1;

        while (opcao != 0){

            System.out.println(
                    "***************************\n" +
                    "Selecione uma opção:" +
                            "\n1. Adicionar nova categoria" +
                            "\n2. Adicionar novo produto" +
                            "\n3. Realizar novo pedido" +
                            "\n4. Listar categorias" +
                            "\n5. Listar produtos" +
                            "\n6. Listar pedidos" +
                            "\n7. Buscar produto pelo nome" +
                            "\n8. Buscar produto por categoria" +
                            "\n9. Buscar produto por maior preço" +
                            "\n10. Buscar produto por menor preço" +
                            "\n11. Buscar produto por termo" +
                            "\n12. Buscar pedido sem data de entrega" +
                            "\n13. Buscar pedidos com data de entrega" +
                            "\n14. Contar produtos por categoria" +
                            "\n15. Buscar produtos com preço maior que um valor" +
                            "\n16. Buscar produtos ordenados pelo preço" +
                            "\n17. Buscar produtos ordenados pelo preço decrescente" +
                            "\n18. Média de preços dos produtos" +
                            "\n19. Contar produtos por categoria(JPQL)" +
                            "\n20. Consultar preço máximo de um produto em uma categoria" +
                            "\n21. Buscar produto pelo nome ou pela categoria" +
                            "\n0. Sair" +
                            "\n***************************"
            );

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
                case 7:
                    buscarProdutoNome();
                    break;
                case 8:
                    buscarProdutoCategoria();
                    break;
                case 9:
                    buscarPorMaiorPreco();
                    break;
                case 10:
                    buscarPorMenorPreco();
                    break;
                case 11:
                    buscarTermo();
                    break;
                case 12:
                    buscarPedidoSemDtEntrega();
                    break;
                case 13:
                    buscarPedidoComDtEntrega();
                    break;
                case 14:
                    contarProdutosCategoria();
                    break;
                case 15:
                    buscarProdutoMaiorValor();
                    break;
                case 16:
                    buscarProdutosOrdenadosPreco();
                    break;
                case 17:
                    buscarProdutosOrdenadosPrecoDesc();
                    break;
                case 18:
                    mediaPrecosProdutos();
                    break;
                case 19:
                    contarProdutosCategoriaJpql();
                    break;
                case 20:
                    precoMaxProdutoCategoria();
                    break;
                case 21:
                    buscarProdutoNomeOuCategoria();
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
        produto.setFornecedor(fornecedorRepository.findById(1L).orElse(null));
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
        pedido.setData(LocalDate.now().plusDays(5));

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
            System.out.println("Código:" + produto.getId() + " - " + "Produto: " + produto.getNome() + " - Preço: " + produto.getPreco() + " - " + "Fornecedor: " + (produto.getFornecedor() != null ? produto.getFornecedor().getNome() : "Sem fornecedor"));


        });
    }

    private void exibirPedidos(){
        pedidoRepository.findAll().forEach(pedido -> {
            System.out.println("Pedido ID: " + pedido.getId() + " - Data: " + pedido.getData());
        });
    }

    private void buscarProdutoNome() {
        System.out.println("Digite o nome do produto que deseja:");
        var nomeProduto = scanner.nextLine();

        Optional<Produto> produto = produtoRepository.findByNome(nomeProduto);

        if (produto.isPresent()){
            System.out.println(produto.get());
        }else {
            System.out.println("Produto não encontrado");
        }

    }

    private void buscarProdutoCategoria() {
        System.out.println("Digite a categoria para busca: ");
        var nomeCategoria = scanner.nextLine();

        Optional<Categoria> categoria = categoriaRepository.findByNomeContainingIgnoreCase(nomeCategoria);

        if (categoria.isPresent()){
            Categoria categoriaEncontrada = categoria.get();
            List<Produto> produtos = produtoRepository.findByCategoria(categoriaEncontrada);
            produtos.forEach(System.out::println);
        }else {
            System.out.println("Categoria não encontrada");
        }
    }

    private void buscarPorMaiorPreco() {
        System.out.println("Qual o valor mínimo deseja pagar?");
        var minValor = scanner.nextDouble();

        List<Produto> produtos = produtoRepository.findByPrecoGreaterThanEqual(minValor);
        produtos.forEach(System.out::println);
    }

    private void buscarPorMenorPreco() {
        System.out.println("Qual o valor máximo que deseja pagar?");
        var maxValor = scanner.nextDouble();

        List<Produto> produtos = produtoRepository.findByPrecoLessThanEqual(maxValor);
        produtos.forEach(System.out::println);
    }

    private void buscarTermo() {
        System.out.println("Digite o nome do produto que deseja:");
        var nomeProduto = scanner.nextLine();

        Optional<Produto> produto = produtoRepository.findByNomeContainingIgnoreCase(nomeProduto);

        if (produto.isPresent()){
            System.out.println(produto.get());
        }else {
            System.out.println("Produto não encontrado");
        }
    }
    private void buscarPedidoSemDtEntrega() {
        System.out.println("Listando pedidos sem data de entrega:");
        List<Pedido> pedidos = pedidoRepository.findByDataNull();
        pedidos.forEach(p -> System.out.println("Pedido: " + p.getId()));
    }
    private void buscarPedidoComDtEntrega() {
        System.out.println("Listando pedidos com data de entrega:");
        List<Pedido> pedidos = pedidoRepository.findByDataNotNull();
        pedidos.forEach(p -> System.out.println("Pedido: " + p.getId() + " com data de entrega para: " + p.getData()));
    }

    private void contarProdutosCategoria() {
        System.out.println("Digite o nome da categoria: ");
        var categoria = scanner.nextLine();
        var contagem = produtoRepository.countByCategoriaNome(categoria);
        System.out.println(contagem);
    }

    private void buscarProdutoMaiorValor() {
        System.out.println("Digite o valor mínimo para o produto:");
        var minValor = scanner.nextInt();
        scanner.nextLine();

        List<Produto> produtos = produtoRepository.produtosPrecoMaior(minValor);
        produtos.forEach(p -> System.out.printf(
                "Produto: %s - Preço: %s \n", p.getNome(),p.getPreco()
        ));
    }

    private void buscarProdutosOrdenadosPreco(){
        System.out.println("Produtos ordenados pelo preço crescente:");
        List<Produto> produtosOrdenados = produtoRepository.buscarProdutosPrecoCrescente();
        produtosOrdenados.forEach(p -> System.out.printf(
                "Produto: %s - Preço: %s \n", p.getNome(),p.getPreco()));
    }

    private void buscarProdutosOrdenadosPrecoDesc(){
        System.out.println("Produtos ordenados pelo preço decrescente:");
        List<Produto> produtosOrdenados = produtoRepository.buscarProdutosPrecoDecrescente();
        produtosOrdenados.forEach(p -> System.out.printf(
                "Produto: %s - Preço: %s \n", p.getNome(),p.getPreco()));
    }

    private void mediaPrecosProdutos(){
        System.out.println("MÉDIA DE PREÇO DOS PRODUTOS:");
        Double media = produtoRepository.consultaMediaPrecosProdutos();
        System.out.printf("R$ %.2f%n",media);
    }

    private void contarProdutosCategoriaJpql() {
        System.out.println("Digite o nome da categoria: ");
        var categoria = scanner.nextLine();

        Integer produtosCategoria = produtoRepository.contarProdutosCategoria(categoria);
        System.out.println("Quantidade de produtos na categoria " + categoria + ": " + produtosCategoria);
    }

    private void precoMaxProdutoCategoria(){
        System.out.println("Digite o nome da categoria: ");
        var categoria = scanner.nextLine();

        Double precoMax = produtoRepository.consultarPrecoMaxCategoria(categoria);
        System.out.println("Preço máximo de produto na categoria " + categoria + ": " + precoMax);
    }
    private void buscarProdutoNomeOuCategoria(){
        System.out.println("Digite o nome do produto ou da categoria:");
        var resposta = scanner.nextLine();

        List<Produto> produtosFiltrados = produtoRepository.buscarProdutosNomeOuCategoria(resposta);
        produtosFiltrados.forEach(p -> System.out.printf(
                "Produto: %s - Preço: %s - Categoria: %s\n", p.getNome(),p.getPreco(), p.getCategoria()));
    }
}
