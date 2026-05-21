package model;

import java.time.LocalDateTime;

public class FavoritoModel {

    private Integer idFavorito, idUsuario, idEmpresa, idCategoria;
    private String nomeFavorito;
    private LocalDateTime dataCriacao;

    // Construtor vazio
    public FavoritoModel() {
    }

    // Construtor sem o ID do favorito (comum para inserções no banco)
    public FavoritoModel(Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito, LocalDateTime dataCriacao) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.idCategoria = idCategoria;
        this.nomeFavorito = nomeFavorito;
        this.dataCriacao = dataCriacao;
    }

    // Construtor completo
    public FavoritoModel(Integer idFavorito, Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito, LocalDateTime dataCriacao) {
        this.idFavorito = idFavorito;
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.idCategoria = idCategoria;
        this.nomeFavorito = nomeFavorito;
        this.dataCriacao = dataCriacao;
    }

    // Getters
    public Integer getIdFavorito() {
        return idFavorito;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public String getNomeFavorito() {
        return nomeFavorito;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    // Setters
    public void setIdFavorito(Integer idFavorito) {
        this.idFavorito = idFavorito;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setNomeFavorito(String nomeFavorito) {
        this.nomeFavorito = nomeFavorito;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}