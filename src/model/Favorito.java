package model;

import java.time.LocalDate;

public class Favorito {

  private int idFavorito;
  private int idUsuario;
  private int idEmpresa;
  private int idCategoria;
  private String nomeFavorito;
  private LocalDate dataCriacao;

  public Favorito(int idFavorito, int idUsuario, int idEmpresa, int idCategoria, String nomeFavorito, LocalDate dataCriacao) {
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
