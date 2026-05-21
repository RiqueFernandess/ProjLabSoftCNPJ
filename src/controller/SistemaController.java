package controller;

import service.*;
import java.util.Scanner;

public class SistemaController {

    private final AuthController authController;
    private final EmpresaController empresaController;
    private final ConsultaController consultaController;
    private final EstatisticaController estatisticaController;
    private final CategoriaController categoriaController;
    private final FavoritoController favoritoController;
    private final UsuarioService usuarioService;
    private final Scanner sc;

    // Construtor com a injeção de todos os controllers e serviços necessários
    public SistemaController(AuthController authController, EmpresaController empresaController,
                             ConsultaController consultaController, EstatisticaController estatisticaController,
                             CategoriaController categoriaController, FavoritoController favoritoController,
                             UsuarioService usuarioService, Scanner sc) {
        this.authController = authController;
        this.empresaController = empresaController;
        this.consultaController = consultaController;
        this.estatisticaController = estatisticaController;
        this.categoriaController = categoriaController;
        this.favoritoController = favoritoController;
        this.usuarioService = usuarioService;
        this.sc = sc;
    }

    // Loop principal que orquestra o estado do sistema (Logado vs Deslogado)
    public void iniciar() {
        while (true) {
            if (!usuarioService.estaLogado()) {
                menuAutenticacao();
            } else {
                menuSistema();
            }
        }
    }

    // Menu inicial para usuários não autenticados
    private void menuAutenticacao() {
        System.out.print("=== LOGIN ===\n1 - Cadastrar\n2 - Login\n0 - Sair\nEscolha uma opção: ");
        String e = sc.nextLine();
        
        if (!e.matches("\\d+")) {
            System.out.println("Digite apenas números.");
            return;
        }
        
        switch (Integer.parseInt(e)) {
            case 1 -> authController.cadastrar();
            case 2 -> authController.login();
            case 0 -> System.exit(0);
            default -> System.out.println("Opção inválida");
        }
    }

    // Menu principal do sistema após o login com sucesso
    private void menuSistema() {
        empresaController.exibirCabecalho();
        System.out.print(
            "1 - Consultar CNPJ\n" +
            "2 - Listar empresas\n" +
            "3 - Buscar empresa\n" +
            "4 - Remover empresa\n" +
            "5 - Minhas consultas\n" +
            "6 - Consultas com erro\n" +
            "7 - Empresas consultadas\n" +
            "8 - Estatísticas\n" +
            "9 - Categorias\n" +
            "10 - Favoritos\n" +
            "11 - Logout\n" +
            "0 - Sair\n" +
            "Escolha uma opção: "                
        );
        String e = sc.nextLine();
        
        if (!e.matches("\\d+")) {
            System.out.println("Digite apenas números.");
            return;
        }
        
        switch (Integer.parseInt(e)) {
            case 1 -> empresaController.consultarCnpj();
            case 2 -> empresaController.listarEmpresas();
            case 3 -> empresaController.buscarEmpresa();
            case 4 -> empresaController.removerEmpresa();
            case 5 -> consultaController.listarUltimasConsultas();
            case 6 -> consultaController.listarConsultasComErro();
            case 7 -> consultaController.listarEmpresasConsultadas();
            case 8 -> estatisticaController.menuEstatisticas();
            case 9 -> menuCategorias();
            case 10 -> menuFavoritos();
            case 11 -> authController.logout();
            case 0 -> System.exit(0);
            default -> System.out.println("Opção inválida");
        }
    }

    // Submenu para gerenciamento de categorias
    private void menuCategorias() {
        while (true) {
            System.out.print("\n=== CATEGORIAS ===\n1 - Cadastrar\n2 - Listar\n3 - Remover\n0 - Voltar\nEscolha uma opção: ");
            String e = sc.nextLine();
            
            if (!e.matches("\\d+")) {
                System.out.println("Digite apenas números.");
                continue;
            }
            
            switch (Integer.parseInt(e)) {
                case 1 -> categoriaController.cadastrar();
                case 2 -> categoriaController.listar();
                case 3 -> categoriaController.remover();
                case 0 -> {
                    return; // Sai do método e volta ao menu anterior
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    // Submenu para gerenciamento de favoritos
    private void menuFavoritos() {
        while (true) {
            System.out.print("\n=== FAVORITOS ===\n1 - Adicionar\n2 - Listar favoritos\n3 - Empresas favoritas\n4 - Remover favorito\n0 - Voltar\nEscolha uma opção: ");
            String e = sc.nextLine();
            
            if (!e.matches("\\d+")) {
                System.out.println("Digite apenas números.");
                continue;
            }
            
            switch (Integer.parseInt(e)) {
                case 1 -> favoritoController.adicionar();
                case 2 -> favoritoController.listar();
                case 3 -> favoritoController.listarEmpresasFavoritas();
                case 4 -> favoritoController.remover();
                case 0 -> {
                    return; // Sai do método e volta ao menu anterior
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }
}