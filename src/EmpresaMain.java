import model.EmpresaModel;
import repository.EmpresaRepository;
import service.EmpresaService;
import util.CnpjValidator;

import java.util.List;
import java.util.Scanner;


//Acredito que esta classe poderia ser uma view;
//Também acho que está sobrecarregada de responsabilidades;
//Pensar em como refatorar;
//Ter em mente um front web (html + css)
public class EmpresaMain {

    private static final Scanner sc = new Scanner(System.in);
    private static final EmpresaService service = new EmpresaService();
    private static final EmpresaRepository empresaRepo = new EmpresaRepository();
    private static int numeroConsultas = 0;
    private static String ultimoCnpj = "";
    private static final int LIMITE_CONSULTAS = 20;
    public static void main(String[] args) {
        while (true) {
            exibirCabecalho();
            exibirMenu();

            String entrada = sc.nextLine();
            int opcao = Integer.parseInt(entrada);

            switch (opcao) {
                case 1 -> consultarCnpj();
                case 2 -> listarEmpresas();
                case 3 -> buscarEmpresa();
                case 4 -> removerEmpresa();
                case 0 -> {System.out.println("Encerrando sistema..."); sc.close(); return;}
                default -> System.out.println("Opção Inválida");
            }
        }
    }

    private static void exibirCabecalho() {
        EmpresaModel ultimaConsulta = empresaRepo.ultimaConsulta();

        System.out.printf("%n%n=== SISTEMA EMPRESA ===%n");
        System.out.printf("Consultas realizadas: %d%n", numeroConsultas);

        if (ultimaConsulta != null) {
            System.out.println(
                "Última consulta: "
                + ultimaConsulta.getNome()
                + " - "
                + ultimaConsulta.getCnpj()
            );
        }
    }

    private static void exibirMenu() {
        System.out.print("Escolha uma opção: ");
        System.out.println("\n1 - Consultar CNPJ" );
        System.out.println("2 - Listar empresas");
        System.out.println("3 - Buscar empresa");
        System.out.println("4 - Remover empresa");
        System.out.println("0 - Sair");
    }

    private static void consultarCnpj() {
        if (numeroConsultas >= LIMITE_CONSULTAS) {
            System.out.println("Limite de consultas atingido.");
            return;
        }

        System.out.print("Digite o CNPJ: ");
        String entrada = sc.nextLine();
        String cnpj = entrada.replaceAll("\\D", "");

        if (cnpj.isBlank()) {
            System.out.println("CNPJ não informado.");
            return;
        }

        if (!CnpjValidator.validar(cnpj)) {
            System.out.println("CNPJ inválido.");
            return;
        }

        if (cnpj.equals(ultimoCnpj)) {
            System.out.println("Consulta repetida.");
            return;
        }

        EmpresaModel empresaExistente = empresaRepo.buscarPorCnpj(cnpj);

        if (empresaExistente != null) {
            System.out.println("\nEmpresa já cadastrada:");
            exibirEmpresa(empresaExistente);
            ultimoCnpj = cnpj;
            numeroConsultas++;
            return;
        }

        System.out.println("Consultando API...");
        EmpresaModel empresa = service.buscar(cnpj);

        if (empresa == null) {
            System.out.println("Empresa não encontrada.");
            return;
        }

        if (!dadosValidos(empresa)) {
            System.out.println("Dados incompletos.");
            return;
        }

        empresaRepo.salvar(empresa);

        System.out.println("\nEmpresa salva com sucesso!");
        exibirEmpresa(empresa);
        ultimoCnpj = cnpj;
        numeroConsultas++;
    }

    private static void listarEmpresas() {
        List<EmpresaModel> lista = empresaRepo.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa cadastrada.");
            return;
        }

        System.out.println("\n=== EMPRESAS ===");
        int contador = 1;
        for (int i = lista.size() - 1; i >= 0; i--) {
            EmpresaModel e = lista.get(i);

            System.out.println(
                contador++
                + " - "
                + e.getCnpj()
                + " - "
                + e.getNome()
            );
        }
    }

    private static void buscarEmpresa() {
        List<EmpresaModel> lista = empresaRepo.listar();
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

        if (!encontrou) { System.out.println("Nenhuma empresa encontrada."); }
    }

    private static void removerEmpresa() {
        List<EmpresaModel> lista = empresaRepo.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa cadastrada.");
            return;
        }

        System.out.println("\n=== REMOVER EMPRESA ===");
        for (int i = 0; i < lista.size(); i++) {
            EmpresaModel e = lista.get(i);
            System.out.println(
                (i + 1)
                + " - "
                + e.getNome()
                + " - "
                + e.getCnpj()
            );
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

        EmpresaModel empresa = lista.get(indice - 1);
        empresaRepo.remover(empresa.getIdEmpresa());

        System.out.println("Empresa removida com sucesso.");
    }

    private static boolean dadosValidos(EmpresaModel e) {
        return
            e.getCnpj() != null &&
            !e.getCnpj().isBlank() &&

            e.getNome() != null &&
            !e.getNome().isBlank() &&

            e.getSituacao() != null &&
            !e.getSituacao().isBlank();
    }

    private static void exibirEmpresa(EmpresaModel e) {
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