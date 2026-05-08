package model;

import java.time.LocalDate;

public class Categoria {
    
    private int idCategoria;
    private int idUsuario;
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;

    public Categoria(int idCategoria, int idUsuario, String nome, String descricao, LocalDate dataCriacao) {
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

}
