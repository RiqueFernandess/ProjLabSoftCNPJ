package model;

import java.time.LocalDate;

public class CategoriaModel {
    
    private Integer idCategoria;
    private Integer idUsuario;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;

    public CategoriaModel(){}
    
    public CategoriaModel(Integer idUsuario, String nome, String descricao, LocalDate dataCriacao) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }
    
    public CategoriaModel(Integer idCategoria, Integer idUsuario, String nome, String descricao, LocalDate dataCriacao) {
        this.idCategoria = idCategoria;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    public int getIdCategoria() { return idCategoria; }
    public int getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataCriacao() { return dataCriacao; }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    

}
