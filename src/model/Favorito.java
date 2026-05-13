package model;

import java.time.LocalDate;

public class Favorito {

  private Integer idFavorito;
  private Integer idUsuario;
  private Integer idEmpresa;
  private Integer idCategoria;
  private String nomeFavorito;
  private LocalDate dataCriacao;

  public Favorito(Integer idFavorito, Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito, LocalDate dataCriacao) {
    this.idFavorito = idFavorito;
    this.idUsuario = idUsuario;
    this.idEmpresa = idEmpresa;
    this.idCategoria = idCategoria;
    this.nomeFavorito = nomeFavorito;
    this.dataCriacao = dataCriacao;
  }

    public int getIdFavorito() { return idFavorito; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdEmpresa() { return idEmpresa; }
    public int getIdCategoria() { return idCategoria; }
    public String getNomeFavorito() { return nomeFavorito; }
    public LocalDate getDataCriacao() { return dataCriacao; }
    
}
