package controller;

import service.UsuarioService;

import java.util.Scanner;

public class AuthController {

    private final UsuarioService usuarioService;
    private final Scanner sc;

    public AuthController(
            UsuarioService usuarioService,
            Scanner sc
    ) {
        this.usuarioService = usuarioService;
        this.sc = sc;
    }

    public void cadastrar() {

        try {

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

            System.out.println("Usuário cadastrado com sucesso");

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public boolean login() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        boolean sucesso = usuarioService.login(email, senha);

        if (!sucesso) {

            System.out.println("Login inválido");

            return false;
        }

        System.out.println(
                "Bem-vindo "
                        + usuarioService
                        .getUsuarioLogado()
                        .getNome()
        );

        return true;
    }

    public void logout() {

        usuarioService.logout();

        System.out.println("Logout realizado");
    }
}