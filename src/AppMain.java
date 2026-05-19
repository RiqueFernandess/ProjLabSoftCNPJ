import controller.AuthController;
import controller.ConsultaController;
import controller.EmpresaController;
import controller.SistemaController;
import service.ConsultaService;
import service.EmpresaService;
import service.UsuarioService;
import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicialização dos Serviços
        UsuarioService usuarioService = new UsuarioService();
        EmpresaService empresaService = new EmpresaService();
        ConsultaService consultaService = new ConsultaService();

        // Inicialização dos Controladores
        AuthController authController = new AuthController(usuarioService, sc);
        EmpresaController empresaController = new EmpresaController(sc, empresaService, consultaService, usuarioService);
        ConsultaController consultaController = new ConsultaController(consultaService, usuarioService);

        // Controlador Central do Sistema
        SistemaController sistemaController = new SistemaController(
                authController, 
                empresaController, 
                consultaController, 
                usuarioService, 
                sc
        );

        sistemaController.iniciar();
    }
}