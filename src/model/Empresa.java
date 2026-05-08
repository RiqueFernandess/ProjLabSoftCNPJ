package model;

public class Empresa {

    private String cnpj;
    private String nome;
    private String situacao;

    public Empresa(String cnpj, String nome, String situacao) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.situacao = situacao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getSituacao() {
        return situacao;
    }
}