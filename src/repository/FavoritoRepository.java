package repository;

import model.FavoritoModel;
import model.EmpresaModel;
import util.ConnectionFactory;
import java.sql.*;
import java.util.*;

public class FavoritoRepository {

    public void salvar(FavoritoModel f) {
        String sql = "INSERT INTO favorito(id_usuario,id_empresa,id_categoria,nome_favorito,data_criacao) VALUES(?,?,?,?,?)";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, f.getIdUsuario());
            ps.setInt(2, f.getIdEmpresa());
            
            if (f.getIdCategoria() != null) {
                ps.setInt(3, f.getIdCategoria());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            ps.setString(4, f.getNomeFavorito());
            ps.setTimestamp(5, Timestamp.valueOf(f.getDataCriacao()));
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar favorito", e);
        }
    }

    public List<FavoritoModel> listar(int idUsuario) {
        List<FavoritoModel> l = new ArrayList<>();
        String sql = "SELECT * FROM favorito WHERE id_usuario=? ORDER BY id_favorito DESC";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FavoritoModel f = new FavoritoModel();
                    f.setIdFavorito(rs.getInt("id_favorito"));
                    f.setIdUsuario(rs.getInt("id_usuario"));
                    f.setIdEmpresa(rs.getInt("id_empresa"));
                    
                    int idCategoria = rs.getInt("id_categoria");
                    if (!rs.wasNull()) {
                        f.setIdCategoria(idCategoria);
                    }
                    
                    f.setNomeFavorito(rs.getString("nome_favorito"));
                    
                    Timestamp t = rs.getTimestamp("data_criacao");
                    if (t != null) {
                        f.setDataCriacao(t.toLocalDateTime());
                    }
                    
                    l.add(f);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar favoritos", e);
        }
        
        return l;
    }

    public List<EmpresaModel> listarEmpresasFavoritas(int idUsuario) {
        List<EmpresaModel> l = new ArrayList<>();
        String sql = "SELECT e.* FROM favorito f INNER JOIN empresa e ON e.id_empresa=f.id_empresa WHERE f.id_usuario=?";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmpresaModel e = new EmpresaModel();
                    e.setIdEmpresa(rs.getInt("id_empresa"));
                    e.setCnpj(rs.getString("cnpj"));
                    e.setNome(rs.getString("nome"));
                    e.setTipoEmpresa(rs.getString("tipo_empresa"));
                    e.setMunicipio(rs.getString("municipio"));
                    
                    if (rs.getDate("data_abertura") != null) {
                        e.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
                    }
                    
                    if (rs.getDate("data_encerramento") != null) {
                        e.setDataEncerramento(rs.getDate("data_encerramento").toLocalDate());
                    }
                    
                    e.setSituacao(rs.getString("situacao"));
                    l.add(e);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar favoritos", e);
        }
        
        return l;
    }

    public void remover(int idFavorito) {
        String sql = "DELETE FROM favorito WHERE id_favorito=?";
        
        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idFavorito);
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover favorito", e);
        }
    }
}