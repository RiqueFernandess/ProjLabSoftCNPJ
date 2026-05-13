package model;

import java.time.LocalDate;

public class EmpresaModel {

    private Integer idEmpresa;

    private String cnpj;
    private String nome;
    private String tipoEmpresa;
    private String municipio;
    private LocalDate dataAbertura;
    private LocalDate dataEncerramento;
    private String situacao;

    //Construtor Vazio
    public EmpresaModel(){}

    //Construtor sem idEmpresa
    public EmpresaModel(String cnpj, String nome, String tipoEmpresa, String municipio, LocalDate dataAbertura, LocalDate dataEncerramento, String situacao) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.tipoEmpresa = tipoEmpresa;
        this.municipio = municipio;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
        this.situacao = situacao;
    }

    //Construtor Completo
    public EmpresaModel(Integer idEmpresa, String cnpj, String nome, String tipoEmpresa, String municipio, LocalDate dataAbertura, LocalDate dataEncerramento, String situacao) {
        this.idEmpresa = idEmpresa;
        this.cnpj = cnpj;
        this.nome = nome;
        this.tipoEmpresa = tipoEmpresa;
        this.municipio = municipio;
        this.dataAbertura = dataAbertura;
        this.dataEncerramento = dataEncerramento;
        this.situacao = situacao;
    }

    public Integer getIdEmpresa() { return idEmpresa; }
    public String getCnpj() { return cnpj; }
    public String getNome() { return nome; }
    public String getTipoEmpresa() { return tipoEmpresa; }
    public String getMunicipio() { return municipio; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public LocalDate getDataEncerramento() { return dataEncerramento; }
    public String getSituacao() { return situacao; }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public void setDataEncerramento(LocalDate dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    
}