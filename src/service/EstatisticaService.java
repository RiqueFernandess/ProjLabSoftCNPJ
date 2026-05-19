package service;

import util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstatisticaService {

    public void exibirTopMunicipiosConsultados() {
        String sql = """
            SELECT municipio, COUNT(*) AS total
            FROM empresa
            WHERE municipio IS NOT NULL AND municipio <> ''
            GROUP BY municipio
            ORDER BY total DESC
            LIMIT 10
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== TOP MUNICÍPIOS CONSULTADOS ===");
            int posicao = 1;
            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                String municipio = rs.getString("municipio");
                int total = rs.getInt("total");
                System.out.println(posicao++ + " - " + municipio + " -> " + total + " consultas");
            }

            if (!encontrou) {
                System.out.println("Nenhum dado encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar estatísticas de municípios", e);
        }
    }

    public void exibirDistribuicaoSituacaoCadastral() {
        String sql = """
            SELECT situacao, COUNT(*) AS total
            FROM empresa
            WHERE situacao IS NOT NULL
            GROUP BY situacao
            ORDER BY total DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== DISTRIBUIÇÃO CADASTRAL ===");
            
            // Estrutura simples para armazenar os dados e evitar o uso de cursores roláveis (beforeFirst)
            List<SituacaoAgrupada> dados = new ArrayList<>();
            int totalEmpresas = 0;

            while (rs.next()) {
                String situacao = rs.getString("situacao");
                int total = rs.getInt("total");
                totalEmpresas += total;
                dados.add(new SituacaoAgrupada(situacao, total));
            }

            if (dados.isEmpty()) {
                System.out.println("Nenhum dado encontrado.");
                return;
            }

            for (SituacaoAgrupada dado : dados) {
                double percentual = totalEmpresas > 0 ? (dado.total * 100.0) / totalEmpresas : 0;
                System.out.printf("%s -> %d empresas (%.2f%%)%n", dado.situacao, dado.total, percentual);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar distribuição cadastral", e);
        }
    }

    public void exibirSegmentacaoPorIdade() {
        String sql = """
            SELECT
                CASE
                    WHEN TIMESTAMPDIFF(YEAR, data_abertura, CURDATE()) < 1 THEN 'Menos de 1 ano'
                    WHEN TIMESTAMPDIFF(YEAR, data_abertura, CURDATE()) BETWEEN 1 AND 5 THEN '1 a 5 anos'
                    ELSE 'Mais de 5 anos'
                END AS faixa,
                COUNT(*) AS total
            FROM empresa
            WHERE data_abertura IS NOT NULL
            GROUP BY faixa
            ORDER BY total DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== SEGMENTAÇÃO POR IDADE ===");
            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                String faixa = rs.getString("faixa");
                int total = rs.getInt("total");
                System.out.println(faixa + " -> " + total + " empresas");
            }

            if (!encontrou) {
                System.out.println("Nenhum dado encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar segmentação por idade", e);
        }
    }

    public void exibirTempoMedioVidaEmpresas() {
        String sql = """
            SELECT municipio, AVG(DATEDIFF(data_encerramento, data_abertura)) / 365 AS media_anos
            FROM empresa
            WHERE data_abertura IS NOT NULL AND data_encerramento IS NOT NULL
            GROUP BY municipio
            ORDER BY media_anos DESC
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== TEMPO MÉDIO DE VIDA DAS EMPRESAS ===");
            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                String municipio = rs.getString("municipio");
                double media = rs.getDouble("media_anos");
                System.out.printf("%s -> %.2f anos%n", municipio, media);
            }

            if (!encontrou) {
                System.out.println("Nenhum dado encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular tempo médio de vida", e);
        }
    }

    // Classe auxiliar interna para transferir os dados da query de situação cadastral
    private static class SituacaoAgrupada {
        final String situacao;
        final int total;

        SituacaoAgrupada(String situacao, int total) {
            this.situacao = situacao;
            this.total = total;
        }
    }

    public void exibirRankingUsuariosMaisAtivos() {
        String sql = """
            SELECT u.nome, COUNT(c.id_consulta) AS total
            FROM consulta c
            INNER JOIN usuario u ON u.id_usuario = c.id_usuario
            GROUP BY u.id_usuario, u.nome
            ORDER BY total DESC
            LIMIT 10
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== RANKING DE USUÁRIOS ===");
            int posicao = 1;
            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;
                String nome = rs.getString("nome");
                int total = rs.getInt("total");
                System.out.println(posicao++ + " - " + nome + " -> " + total + " consultas");
            }

            if (!encontrou) {
                System.out.println("Nenhum dado encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar ranking de usuários", e);
        }
    }

    public void exibirMediaConsultasPorUsuario() {
        String sql = """
            SELECT AVG(total_consultas) AS media
            FROM (
                SELECT COUNT(*) AS total_consultas
                FROM consulta
                GROUP BY id_usuario
            ) t
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            System.out.println("\n=== MÉDIA DE CONSULTAS POR USUÁRIO ===");

            if (rs.next()) {
                double media = rs.getDouble("media");
                System.out.printf("Média geral: %.2f consultas por usuário%n", media);
            } else {
                System.out.println("Nenhum dado encontrado.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular média de consultas", e);
        }
    }

    public void exibirUsoQuota(int idUsuario, int limite) {
        String sql = """
            SELECT COUNT(*) AS total
            FROM consulta
            WHERE id_usuario = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    double percentual = (total * 100.0) / limite;

                    System.out.println("\n=== USO DA QUOTA ===");
                    System.out.println("Consultas realizadas: " + total + "/" + limite);
                    System.out.printf("Uso da franquia: %.2f%%%n", percentual);

                    if (percentual >= 80) {
                        System.out.println("Atenção: Seu limite de consultas está próximo do fim!");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular uso da quota", e);
        }
    }
}