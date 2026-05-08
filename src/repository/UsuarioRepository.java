package repository;

import model.Usuario;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

public class UsuarioRepository {
    
    public void salvar(Usuario usuario) {

        String sql =
            "INSERT INTO usuario (nome, cpf, email, senha, dataCriacao, ativo) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getSenha());
            ps.setDate(5, usuario.getDataCriacao() != null ? java.sql.Date.valueOf(usuario.getDataCriacao()) : null);// Converte LocalDate para java.sql.Date, ou seta null se dataCriacao for null
            ps.setBoolean(6, usuario.getAtivo());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
