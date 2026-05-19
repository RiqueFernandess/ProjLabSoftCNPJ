package controller;

import model.ConsultaModel;
import model.EmpresaModel;
import model.UsuarioModel;
import service.ConsultaService;
import service.UsuarioService;
import java.util.List;

public class ConsultaController {

    private final ConsultaService consultaService;
    private final UsuarioService usuarioService;

    public ConsultaController(ConsultaService consultaService, UsuarioService usuarioService) {
        this.consultaService = consultaService;
        this.usuarioService = usuarioService;
    }

    public void listarUltimasConsultas() {
        UsuarioModel usuario = usuarioService.getUsuarioLogado();
        List<ConsultaModel> lista = consultaService.listarUltimasPorUsuario(usuario.getIdUsuario());

        if (lista.isEmpty()) {
            System.out.println("\nNenhuma consulta encontrada.");
            return;
        }

        System.out.println("\n=== ÚLTIMAS CONSULTAS ===");
        for (ConsultaModel c : lista) {
            System.out.println("\nID Consulta: " + c.getIdConsulta());
            System.out.println("Empresa ID: " + c.getIdEmpresa());
            System.out.println("Data/Hora: " + c.getDataHora());
            System.out.println("Sucesso: " + c.isSucesso());
            System.out.println("Tempo resposta: " + c.getTempoRespostaMs() + "ms");
            
            if (c.getMsgErro() != null) {
                System.out.println("Erro: " + c.getMsgErro());
            }
            System.out.println("---------------------------");
        }
    }

    public void listarConsultasComErro() {
        UsuarioModel usuario = usuarioService.getUsuarioLogado();
        List<ConsultaModel> lista = consultaService.listarComErro(usuario.getIdUsuario());

        if (lista.isEmpty()) {
            System.out.println("\nNenhuma consulta com erro.");
            return;
        }

        System.out.println("\n=== CONSULTAS COM ERRO ===");
        for (ConsultaModel c : lista) {
            System.out.println("\nID Consulta: " + c.getIdConsulta());
            System.out.println("Empresa ID: " + c.getIdEmpresa());
            System.out.println("Data/Hora: " + c.getDataHora());
            System.out.println("Mensagem erro: " + c.getMsgErro());
            System.out.println("Tempo resposta: " + c.getTempoRespostaMs() + "ms");
            System.out.println("---------------------------");
        }
    }

    public void listarEmpresasConsultadas() {
        UsuarioModel usuario = usuarioService.getUsuarioLogado();
        List<EmpresaModel> lista = consultaService.listarEmpresasConsultadas(usuario.getIdUsuario());

        if (lista.isEmpty()) {
            System.out.println("\nNenhuma empresa consultada.");
            return;
        }

        System.out.println("\n=== EMPRESAS CONSULTADAS ===");
        int contador = 1;
        for (EmpresaModel e : lista) {
            System.out.println(contador++ + " - " + e.getCnpj() + " - " + e.getNome());
        }
    }

}