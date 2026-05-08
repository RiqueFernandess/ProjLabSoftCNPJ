package model;

import java.time.LocalDate;

public class Empresa {

    private int idEmpresa;
    private String cnpj;
    private String nome;
    private String tipoEmpresa;
    private String municipio;
    private LocalDate dataAbertura;
    private LocalDate dataEncerramento;
    private String situacao;

    public Empresa(int idEmpresa, String cnpj, String nome, String tipoEmpresa, String municipio, LocalDate dataAbertura, LocalDate dataEncerramento, String situacao) {
        this.idEmpresa = idEmpresa;
        this.cnpj = cnpj;
        this.nome = nome;
        this.tipoEmpresa = tipoEmpresa;
        this.municipio = municipio;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
        this.situacao = situacao;
    }

    public int getIdEmpresa() { return idEmpresa; }
    public String getCnpj() { return cnpj; }
    public String getNome() { return nome; }
    public String getTipoEmpresa() { return tipoEmpresa; }
    public String getMunicipio() { return municipio; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public LocalDate getDataEncerramento() { return dataEncerramento; }
    public String getSituacao() { return situacao; }
}