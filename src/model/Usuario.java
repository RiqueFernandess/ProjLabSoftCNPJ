package model;

import java.time.LocalDate;

public class Usuario {
    
    private Integer idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataCriacao;
    private Boolean ativo;

    public Usuario(){}

    public Usuario(String nome, String cpf, String email, String senha, LocalDate dataCriacao, Boolean ativo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }

    public Usuario(Integer idUsuario, String nome, String cpf, String email, String senha, LocalDate dataCriacao, Boolean ativo) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.ativo = ativo;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    public Boolean getAtivo() { return ativo; }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    

}
