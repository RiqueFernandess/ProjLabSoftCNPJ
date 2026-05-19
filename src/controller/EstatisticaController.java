package controller;

import service.EstatisticaService;
import service.UsuarioService;

import java.util.Scanner;

public class EstatisticaController {

    private final EstatisticaService estatisticaService;
    private final UsuarioService usuarioService;
    private final Scanner sc;

    public EstatisticaController(Scanner sc, UsuarioService usuarioService) {
        this.sc = sc;
        this.estatisticaService = new EstatisticaService();
        this.usuarioService = usuarioService;
    }

    public void menuEstatisticas() {
        while (true) {

            System.out.print("""    
                === ESTATÍSTICAS ===
                1 - Top municípios consultados
                2 - Distribuição por situação cadastral
                3 - Segmentação por idade da empresa
                4 - Tempo médio de vida das empresas
                5 - Ranking Usuários Mais Ativos
                6 - Media Consultas por Usuário
                7 - Uso de Quota
                0 - Voltar
                
                Escolha uma opção:\s"""
        );

            String entrada = sc.nextLine();

            if (!entrada.matches("\\d+")) {
                System.out.println("Digite apenas números.");
                continue;
            }

            int opcao = Integer.parseInt(entrada);

            switch (opcao) {
                case 1 -> estatisticaService.exibirTopMunicipiosConsultados();
                case 2 -> estatisticaService.exibirDistribuicaoSituacaoCadastral();
                case 3 -> estatisticaService.exibirSegmentacaoPorIdade();
                case 4 -> estatisticaService.exibirTempoMedioVidaEmpresas();
                case 5 -> estatisticaService.exibirRankingUsuariosMaisAtivos();
                case 6 -> estatisticaService.exibirMediaConsultasPorUsuario();
                case 7 -> usoQuota();
                case 0 -> { return; }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void usoQuota() {
        int idUsuario = usuarioService.getUsuarioLogado().getIdUsuario();
        estatisticaService.exibirUsoQuota(idUsuario, 20);
    }
}