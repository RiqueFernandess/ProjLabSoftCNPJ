package repository;

import model.CategoriaModel;
import util.ConnectionFactory;
import java.sql.*;
import java.util.*;

public class CategoriaRepository {

    public void salvar(CategoriaModel c) {
        String sql = "INSERT INTO categoria(id_usuario,nome,descricao,data_criacao) VALUES(?,?,?,?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, c.getIdUsuario());
            ps.setString(2, c.getNome());
            ps.setString(3, c.getDescricao());
            ps.setTimestamp(4, Timestamp.valueOf(c.getDataCriacao()));
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar categoria", e);
        }
    }

    public List<CategoriaModel> listar(int idUsuario) {
        List<CategoriaModel> l = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE id_usuario=? ORDER BY id_categoria DESC";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CategoriaModel c = new CategoriaModel();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    c.setIdUsuario(rs.getInt("id_usuario"));
                    c.setNome(rs.getString("nome"));
                    c.setDescricao(rs.getString("descricao"));
                    
                    Timestamp t = rs.getTimestamp("data_criacao");
                    if (t != null) {
                        c.setDataCriacao(t.toLocalDateTime());
                    }
                    
                    l.add(c);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar categorias", e);
        }
        
        return l;
    }

    public void remover(int idCategoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria=?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCategoria);
            ps.executeUpdate();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover categoria", e);
        }
    }
}