package com.curso.gerenciador_pedidos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Produto> produtos;

    public Categoria() {
    }

    @Override
    public String toString() {
        return nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        produtos.forEach(p -> p.setCategoria(this));
        this.produtos = produtos;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
