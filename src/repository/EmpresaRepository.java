package repository;

import model.Empresa;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {

    public void salvar(Empresa empresa) {

        String sql =
            "INSERT INTO empresa (cnpj, nome, tipo_empresa, municipio, data_abertura, data_e1ncerramento, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, empresa.getCnpj());
            ps.setString(2, empresa.getNome());
            ps.setString(3, empresa.getTipoEmpresa());
            ps.setString(4, empresa.getMunicipio());
            ps.setDate(5, empresa.getDataAbertura() != null ? java.sql.Date.valueOf(empresa.getDataAbertura()) : null);
            ps.setDate(6, empresa.getDataEncerramento() != null ? java.sql.Date.valueOf(empresa.getDataEncerramento()) : null);
            ps.setString(7, empresa.getSituacao());

            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Empresa> listar() {

    List<Empresa> lista = new ArrayList<>();

    String sql = "SELECT * FROM empresa ORDER BY id_empresa DESC";

    try (
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            Empresa empresa = new Empresa(
                rs.getInt("id_empresa"),
                rs.getString("cnpj"),
                rs.getString("nome"),
                rs.getString("tipo_empresa"),
                rs.getString("municipio"),

                rs.getDate("data_abertura") != null
                    ? rs.getDate("data_abertura").toLocalDate()
                    : null,

                rs.getDate("data_encerramento") != null
                    ? rs.getDate("data_encerramento").toLocalDate()
                    : null,

                rs.getString("situacao")
            );

            lista.add(empresa);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

        return lista;
    }

    public Empresa ultimaConsulta() {

        String sql = "SELECT * FROM empresa ORDER BY id_empresa DESC LIMIT 1";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {

                return new Empresa(
                    rs.getInt("id_empresa"),
                    rs.getString("cnpj"),
                    rs.getString("nome"),
                    rs.getString("tipo_empresa"),
                    rs.getString("municipio"),

                    rs.getDate("data_abertura") != null
                        ? rs.getDate("data_abertura").toLocalDate()
                        : null,

                    rs.getDate("data_encerramento") != null
                        ? rs.getDate("data_encerramento").toLocalDate()
                        : null,

                    rs.getString("situacao")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    return null;
    }

    public Empresa buscarPorCnpj(String cnpj) {

        String sql = "SELECT * FROM empresa WHERE cnpj = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, cnpj);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Empresa(
                    rs.getInt("id_empresa"),
                    rs.getString("cnpj"),
                    rs.getString("nome"),
                    rs.getString("tipo_empresa"),
                    rs.getString("municipio"),

                    rs.getDate("data_abertura") != null
                        ? rs.getDate("data_abertura").toLocalDate()
                        : null,

                    rs.getDate("data_encerramento") != null
                        ? rs.getDate("data_encerramento").toLocalDate()
                        : null,

                    rs.getString("situacao")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void remover(int idEmpresa) {

        String sql = "DELETE FROM empresa WHERE id_empresa = ?";

        try (
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, idEmpresa);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}