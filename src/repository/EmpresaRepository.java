package repository;

import model.EmpresaModel;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {

    public void salvar(EmpresaModel empresa) {
        String sql = """
        INSERT INTO empresa (cnpj, nome, tipo_empresa, municipio, data_abertura, data_encerramento, situacao)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empresa.getCnpj());
            ps.setString(2, empresa.getNome());
            ps.setString(3, empresa.getTipoEmpresa());
            ps.setString(4, empresa.getMunicipio());
            ps.setDate(5, empresa.getDataAbertura() != null ? java.sql.Date.valueOf(empresa.getDataAbertura()) : null);
            ps.setDate(6, empresa.getDataEncerramento() != null ? java.sql.Date.valueOf(empresa.getDataEncerramento()) : null);
            ps.setString(7, empresa.getSituacao());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    empresa.setIdEmpresa(keys.getInt(1));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar empresa", e);
        }
    }

    public List<EmpresaModel> listar() {
        List<EmpresaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresa";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmpresaModel empresa = new EmpresaModel();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setTipoEmpresa(rs.getString("tipo_empresa"));
                empresa.setMunicipio(rs.getString("municipio"));
                empresa.setDataAbertura(rs.getDate("data_abertura") != null ? rs.getDate("data_abertura").toLocalDate() : null);
                empresa.setDataEncerramento(rs.getDate("data_encerramento") != null ? rs.getDate("data_encerramento").toLocalDate() : null);
                empresa.setSituacao(rs.getString("situacao"));

                lista.add(empresa);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar empresas", e);
        }
        return lista;
    }

    public EmpresaModel buscarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM empresa WHERE cnpj = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cnpj);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EmpresaModel(
                        rs.getInt("id_empresa"),
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("tipo_empresa"),
                        rs.getString("municipio"),
                        rs.getDate("data_abertura") != null ? rs.getDate("data_abertura").toLocalDate() : null,
                        rs.getDate("data_encerramento") != null ? rs.getDate("data_encerramento").toLocalDate() : null,
                        rs.getString("situacao")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar empresa por CNPJ", e);
        }
        return null;
    }

    public EmpresaModel ultimaConsulta() {
        String sql = "SELECT * FROM empresa ORDER BY id_empresa DESC LIMIT 1";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new EmpresaModel(
                    rs.getInt("id_empresa"),
                    rs.getString("cnpj"),
                    rs.getString("nome"),
                    rs.getString("tipo_empresa"),
                    rs.getString("municipio"),
                    rs.getDate("data_abertura") != null ? rs.getDate("data_abertura").toLocalDate() : null,
                    rs.getDate("data_encerramento") != null ? rs.getDate("data_encerramento").toLocalDate() : null,
                    rs.getString("situacao")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar última consulta", e);
        }
        return null;
    }

    public void remover(int idEmpresa) {
        String sql = "DELETE FROM empresa WHERE id_empresa = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover empresa", e);
        }
    }
}