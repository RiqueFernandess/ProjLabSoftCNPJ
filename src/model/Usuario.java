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

}
