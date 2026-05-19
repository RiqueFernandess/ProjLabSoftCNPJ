package repository;

import model.ConsultaModel;
import model.EmpresaModel;
import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    public void salvar(ConsultaModel consulta) {
        String sql = "INSERT INTO consulta (id_usuario, id_empresa, data_hora, sucesso, msg_erro, tempo_resposta_ms) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, consulta.getIdUsuario());
            if (consulta.getIdEmpresa() != null) {
                ps.setInt(2, consulta.getIdEmpresa());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            ps.setBoolean(4, consulta.isSucesso());
            ps.setString(5, consulta.getMsgErro());
            ps.setInt(6, consulta.getTempoRespostaMs());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar consulta", e);
        }
    }

    public List<ConsultaModel> listarUltimasPorUsuario(int idUsuario) {
        List<ConsultaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE id_usuario = ? ORDER BY data_hora DESC LIMIT 10";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConsultaModel consulta = new ConsultaModel();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setIdUsuario(rs.getInt("id_usuario"));
                
                int idEmpresa = rs.getInt("id_empresa");
                if (!rs.wasNull()) {
                    consulta.setIdEmpresa(idEmpresa);
                }
                
                Timestamp timestamp = rs.getTimestamp("data_hora");
                if (timestamp != null) {
                    consulta.setDataHora(timestamp.toLocalDateTime());
                }
                
                consulta.setSucesso(rs.getBoolean("sucesso"));
                consulta.setMsgErro(rs.getString("msg_erro"));
                consulta.setTempoRespostaMs(rs.getInt("tempo_resposta_ms"));
                lista.add(consulta);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar consultas", e);
        }
        return lista;
    }

    public List<ConsultaModel> listarComErro(int idUsuario) {
        List<ConsultaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM consulta WHERE id_usuario = ? AND sucesso = false ORDER BY data_hora DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ConsultaModel consulta = new ConsultaModel();
                consulta.setIdConsulta(rs.getInt("id_consulta"));
                consulta.setIdUsuario(rs.getInt("id_usuario"));
                
                int idEmpresa = rs.getInt("id_empresa");
                if (!rs.wasNull()) {
                    consulta.setIdEmpresa(idEmpresa);
                }
                
                Timestamp timestamp = rs.getTimestamp("data_hora");
                if (timestamp != null) {
                    consulta.setDataHora(timestamp.toLocalDateTime());
                }
                
                consulta.setSucesso(rs.getBoolean("sucesso"));
                consulta.setMsgErro(rs.getString("msg_erro"));
                consulta.setTempoRespostaMs(rs.getInt("tempo_resposta_ms"));
                lista.add(consulta);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar consultas com erro", e);
        }
        return lista;
    }

    public List<EmpresaModel> listarEmpresasConsultadas(int idUsuario) {
        List<EmpresaModel> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT e.* FROM consulta c INNER JOIN empresa e ON e.id_empresa = c.id_empresa WHERE c.id_usuario = ? ORDER BY c.data_hora DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EmpresaModel empresa = new EmpresaModel();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setTipoEmpresa(rs.getString("tipo_empresa"));
                empresa.setMunicipio(rs.getString("municipio"));
                
                if (rs.getDate("data_abertura") != null) {
                    empresa.setDataAbertura(rs.getDate("data_abertura").toLocalDate());
                }
                if (rs.getDate("data_encerramento") != null) {
                    empresa.setDataEncerramento(rs.getDate("data_encerramento").toLocalDate());
                }
                
                empresa.setSituacao(rs.getString("situacao"));
                lista.add(empresa);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar empresas consultadas", e);
        }
        return lista;
    }
}