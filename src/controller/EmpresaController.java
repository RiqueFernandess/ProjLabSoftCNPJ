package controller;

import model.EmpresaModel;
import service.ConsultaService;
import service.EmpresaService;
import java.util.List;
import java.util.Scanner;

public class EmpresaController {
    private final Scanner sc;
    private final EmpresaService empresaService;
    private final ConsultaService consultaService;

    public EmpresaController() {
        this.sc = new Scanner(System.in);
        this.empresaService = new EmpresaService();
        this.consultaService = new ConsultaService();
    }

    public void iniciar() {
        boolean executando = true;
        while (executando) {
            exibirCabecalho();
            exibirMenu();
            String entrada = sc.nextLine();
            if (!entrada.matches("\\d+")) {
                System.out.println("Digite apenas números.");
                continue;
            }
            int opcao = Integer.parseInt(entrada);
            switch (opcao) {
                case 1 -> consultarCnpj();
                case 2 -> listarEmpresas();
                case 3 -> buscarEmpresa();
                case 4 -> removerEmpresa();
                case 0 -> {
                    System.out.println("Encerrando sistema...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private void exibirCabecalho() {
        EmpresaModel ultimaConsulta = empresaService.obterUltimaConsulta();
        System.out.println("\n=== SISTEMA EMPRESA ===");
        System.out.println("Consultas realizadas: " + consultaService.getNumeroConsultas());
        if (ultimaConsulta != null) {
            System.out.println("Última consulta: " + ultimaConsulta.getNome() + " - " + ultimaConsulta.getCnpj());
        }
    }

    private void exibirMenu() {
        System.out.println("\n1 - Consultar CNPJ\n2 - Listar empresas\n3 - Buscar empresa\n4 - Remover empresa\n0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void consultarCnpj() {
        if (consultaService.atingiuLimite()) {
            System.out.println("Limite de consultas atingido.");
            return;
        }
        System.out.print("Digite o CNPJ: ");
        String cnpj = sc.nextLine().replaceAll("\\D", "");
        if (consultaService.consultaRepetida(cnpj)) {
            System.out.println("Consulta repetida.");
            return;
        }
        System.out.println("Consultando empresa...");
        EmpresaModel empresa = empresaService.consultarPorCnpj(cnpj);
        if (empresa == null) {
            System.out.println("Empresa não encontrada.");
            return;
        }
        exibirEmpresa(empresa);
        consultaService.registrarConsulta(cnpj);
    }

    private void listarEmpresas() {
        List<EmpresaModel> lista = empresaService.listarEmpresas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa cadastrada.");
            return;
        }
        System.out.println("\n=== EMPRESAS ===");
        int contador = 1;
        for (int i = lista.size() - 1; i >= 0; i--) {
            EmpresaModel e = lista.get(i);
            System.out.println(contador++ + " - " + e.getCnpj() + " - " + e.getNome());
        }
    }

    private void buscarEmpresa() {
        List<EmpresaModel> lista = empresaService.listarEmpresas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa cadastrada.");
            return;
        }
        System.out.print("Digite nome ou CNPJ: ");
        String busca = sc.nextLine().trim();
        String buscaCnpj = busca.replaceAll("\\D", "");
        boolean encontrou = false;
        for (EmpresaModel e : lista) {
            boolean matchCnpj = buscaCnpj.length() >= 3 && e.getCnpj().contains(buscaCnpj);
            boolean matchNome = e.getNome().toLowerCase().contains(busca.toLowerCase());
            if (matchCnpj || matchNome) {
                exibirEmpresa(e);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhuma empresa encontrada.");
    }

    private void removerEmpresa() {
        List<EmpresaModel> lista = empresaService.listarEmpresas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa cadastrada.");
            return;
        }
        System.out.println("\n=== REMOVER EMPRESA ===");
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNome() + " - " + lista.get(i).getCnpj());
        }
        System.out.print("Escolha o número: ");
        String entrada = sc.nextLine();
        if (!entrada.matches("\\d+")) {
            System.out.println("Digite apenas números.");
            return;
        }
        int indice = Integer.parseInt(entrada);
        if (indice < 1 || indice > lista.size()) {
            System.out.println("Número inválido.");
            return;
        }
        empresaService.removerEmpresa(lista.get(indice - 1).getIdEmpresa());
        System.out.println("Empresa removida com sucesso.");
    }

    private void exibirEmpresa(EmpresaModel e) {
        System.out.println("\n---------------------------");
        System.out.println("ID: " + e.getIdEmpresa());
        System.out.println("CNPJ: " + e.getCnpj());
        System.out.println("Nome: " + e.getNome());
        System.out.println("Tipo: " + e.getTipoEmpresa());
        System.out.println("Município: " + e.getMunicipio());
        System.out.println("Abertura: " + e.getDataAbertura());
        System.out.println("Encerramento: " + e.getDataEncerramento());
        System.out.println("Situação: " + e.getSituacao());
    }
}