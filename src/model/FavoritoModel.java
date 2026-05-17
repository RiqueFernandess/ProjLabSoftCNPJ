package model;

import java.time.LocalDate;

public class FavoritoModel {

  private Integer idFavorito;
  private Integer idUsuario;
  private Integer idEmpresa;
  private Integer idCategoria;
  private String nomeFavorito;
  private LocalDate dataCriacao;

  public FavoritoModel(){}

  public FavoritoModel(Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito, LocalDate dataCriacao) {
    this.idUsuario = idUsuario;
    this.idEmpresa = idEmpresa;
    this.idCategoria = idCategoria;
    this.nomeFavorito = nomeFavorito;
    this.dataCriacao = dataCriacao;
  }

  public FavoritoModel(Integer idFavorito, Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito, LocalDate dataCriacao) {
    this.idFavorito = idFavorito;
    this.idUsuario = idUsuario;
    this.idEmpresa = idEmpresa;
    this.idCategoria = idCategoria;
    this.nomeFavorito = nomeFavorito;
    this.dataCriacao = dataCriacao;
  }

    public Integer getIdFavorito() { return idFavorito; }
    public Integer getIdUsuario() { return idUsuario; }
    public Integer getIdEmpresa() { return idEmpresa; }
    public Integer getIdCategoria() { return idCategoria; }
    public String getNomeFavorito() { return nomeFavorito; }
    public LocalDate getDataCriacao() { return dataCriacao; }

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

    public void setDataCriacao(LocalDate dataCriacao) {
      this.dataCriacao = dataCriacao;
    }
    

    
}
