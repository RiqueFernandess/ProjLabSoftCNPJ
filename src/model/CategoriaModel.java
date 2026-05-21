package model;

import java.time.LocalDateTime;

public class CategoriaModel {

    private Integer idCategoria;
    private Integer idUsuario;
    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;

    // Construtor padrão (vazio)
    public CategoriaModel() {
    }

    // Construtor sem o ID (útil para inserções no banco de dados)
    public CategoriaModel(Integer idUsuario, String nome, String descricao, LocalDateTime dataCriacao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    // Construtor completo
    public CategoriaModel(Integer idCategoria, Integer idUsuario, String nome, String descricao, LocalDateTime dataCriacao) {
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}