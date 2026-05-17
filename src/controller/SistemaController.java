package controller;

import service.UsuarioService;

import java.util.Scanner;

public class SistemaController {

    private final AuthController authController;

    private final EmpresaController empresaController;

    private final UsuarioService usuarioService;

    private final Scanner sc;

    public SistemaController(
            AuthController authController,
            EmpresaController empresaController,
            UsuarioService usuarioService,
            Scanner sc
    ) {

        this.authController = authController;
        this.empresaController = empresaController;
        this.usuarioService = usuarioService;
        this.sc = sc;
    }

    public void iniciar() {

        while (true) {

            if (!usuarioService.estaLogado()) {

                menuAutenticacao();

            } else {

                menuSistema();
            }
        }
    }

    private void menuAutenticacao() {

        System.out.println("""

                === LOGIN ===
                1 - Cadastrar
                2 - Login
                0 - Sair
                """);

        System.out.print("Escolha uma opção: ");

        String entrada = sc.nextLine();

        if (!entrada.matches("\\d+")) {

            System.out.println("Digite apenas números.");

            return;
        }

        int opcao = Integer.parseInt(entrada);

        switch (opcao) {

            case 1 -> authController.cadastrar();

            case 2 -> authController.login();

            case 0 -> System.exit(0);

            default -> System.out.println("Opção inválida");
        }
    }

    private void menuSistema() {

        empresaController.exibirCabecalho();

        System.out.println("""

                1 - Consultar CNPJ
                2 - Listar empresas
                3 - Buscar empresa
                4 - Remover empresa
                5 - Logout
                0 - Sair
                """);

        System.out.print("Escolha uma opção: ");

        String entrada = sc.nextLine();

        if (!entrada.matches("\\d+")) {

            System.out.println("Digite apenas números.");

            return;
        }

        int opcao = Integer.parseInt(entrada);

        switch (opcao) {

            case 1 -> empresaController.consultarCnpj();

            case 2 -> empresaController.listarEmpresas();

            case 3 -> empresaController.buscarEmpresa();

            case 4 -> empresaController.removerEmpresa();

            case 5 -> authController.logout();

            case 0 -> System.exit(0);

            default -> System.out.println("Opção inválida");
        }
    }
}