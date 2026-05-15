package controller;

import service.UsuarioService;

import java.util.Scanner;

public class AuthController {

    private final UsuarioService usuarioService;

    private final Scanner sc;

    public AuthController() {

        usuarioService = new UsuarioService();

        sc = new Scanner(System.in);
    }

    public void iniciar() {

        while (true) {

            System.out.println("\n=== LOGIN ===");

            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("0 - Sair");

            int op =
                    Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1 -> cadastrar();

                case 2 -> login();

                case 0 -> System.exit(0);
            }
        }
    }

    private void cadastrar() {

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        usuarioService.cadastrar(
                nome,
                cpf,
                email,
                senha
        );

        System.out.println(
                "Usuário cadastrado com sucesso"
        );
    }

    private void login() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        boolean sucesso =
                usuarioService.login(email, senha);

        if (!sucesso) {

            System.out.println(
                    "Login inválido"
            );

            return;
        }

        System.out.println(
                "Bem-vindo "
                        + usuarioService
                        .getUsuarioLogado()
                        .getNome()
        );

        menuSistema();
    }

    private void menuSistema() {

        while (usuarioService.estaLogado()) {

            System.out.println("\n=== SISTEMA ===");

            System.out.println(
                    "1 - Consultar empresa"
            );

            System.out.println(
                    "2 - Logout"
            );

            int op =
                    Integer.parseInt(sc.nextLine());

            switch (op) {

                case 1 -> {

                    System.out.println(
                            "Consulta de empresa aqui"
                    );
                }

                case 2 -> {

                    usuarioService.logout();

                    System.out.println(
                            "Logout realizado"
                    );
                }
            }
        }
    }
}