package repository;

import model.Empresa;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmpresaRepository {

    public void salvar(Empresa empresa) {

        String sql =
            "INSERT INTO empresa (cnpj, nome, situacao) VALUES (?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, empresa.getCnpj());
            ps.setString(2, empresa.getNome());
            ps.setString(3, empresa.getSituacao());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listar() {

        String sql = "SELECT * FROM empresa";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                System.out.println(
                    rs.getString("cnpj") + " - " +
                    rs.getString("nome") + " - " +
                    rs.getString("situacao")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}