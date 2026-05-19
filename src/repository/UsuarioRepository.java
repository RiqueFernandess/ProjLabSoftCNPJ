package repository;

import model.UsuarioModel;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class UsuarioRepository {

    public void salvar(UsuarioModel usuario) {
        String sql = """
            INSERT INTO usuario (nome, cpf, email, senha, data_criacao, ativo)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());
            ps.setDate(5, usuario.getDataCriacao() != null ? Date.valueOf(usuario.getDataCriacao()) : null);
            ps.setBoolean(6, usuario.getAtivo());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    public UsuarioModel buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioModel usuario = new UsuarioModel();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setCpf(rs.getString("cpf"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setAtivo(rs.getBoolean("ativo"));

                    if (rs.getDate("data_criacao") != null) {
                        usuario.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
                    }

                    return usuario;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return null;
    }
}