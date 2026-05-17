import controller.AuthController;
import controller.EmpresaController;
import controller.SistemaController;

import service.ConsultaService;
import service.EmpresaService;
import service.UsuarioService;

import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UsuarioService usuarioService =
                new UsuarioService();

        EmpresaService empresaService =
                new EmpresaService();

        ConsultaService consultaService =
                new ConsultaService();

        AuthController authController =
                new AuthController(
                        usuarioService,
                        sc
                );

        EmpresaController empresaController =
                new EmpresaController(
                        sc,
                        empresaService,
                        consultaService
                );

        SistemaController sistemaController =
                new SistemaController(
                        authController,
                        empresaController,
                        usuarioService,
                        sc
                );

        sistemaController.iniciar();
    }
}