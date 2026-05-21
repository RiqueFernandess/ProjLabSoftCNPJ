import controller.*;
import service.*;
import java.util.Scanner;

public class AppMainTerminal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Instanciação da camada de Serviços (Business/Service Layer)
        UsuarioService usuarioService = new UsuarioService();
        EmpresaService empresaService = new EmpresaService();
        ConsultaService consultaService = new ConsultaService();
        CategoriaService categoriaService = new CategoriaService();
        FavoritoService favoritoService = new FavoritoService();

        // Inicialização e Injeção de Dependências na camada de Controle (Controllers)
        AuthController authController = new AuthController(usuarioService, sc);
        
        EmpresaController empresaController = new EmpresaController(
            sc, empresaService, consultaService, usuarioService
        );
        
        ConsultaController consultaController = new ConsultaController(
            consultaService, usuarioService
        );
        
        EstatisticaController estatisticaController = new EstatisticaController(
            sc, usuarioService
        );
        
        CategoriaController categoriaController = new CategoriaController(
            sc, categoriaService, usuarioService
        );
        
        FavoritoController favoritoController = new FavoritoController(
            sc, favoritoService, usuarioService
        );

        // Orquestrador do ecossistema de menus da aplicação
        SistemaController sistemaController = new SistemaController(
            authController, empresaController, consultaController, 
            estatisticaController, categoriaController, favoritoController, 
            usuarioService, sc
        );

        // Inicialização do loop principal do sistema
        sistemaController.iniciar();
    }
}