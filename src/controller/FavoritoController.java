package controller;

import model.EmpresaModel;
import model.FavoritoModel;
import model.UsuarioModel;
import service.FavoritoService;
import service.UsuarioService;
import java.util.*;

public class FavoritoController {

    private final Scanner sc;
    private final FavoritoService favoritoService;
    private final UsuarioService usuarioService;

    // Construtor recebendo as dependências necessárias
    public FavoritoController(Scanner sc, FavoritoService favoritoService, UsuarioService usuarioService) {
        this.sc = sc;
        this.favoritoService = favoritoService;
        this.usuarioService = usuarioService;
    }

    // Fluxo de criação de um favorito via console
    public void adicionar() {
        UsuarioModel u = usuarioService.getUsuarioLogado();
        
        System.out.print("ID Empresa: ");
        String idEmpresa = sc.nextLine();
        
        System.out.print("ID Categoria(opcional): ");
        String idCategoria = sc.nextLine();
        
        System.out.print("Nome Favorito: ");
        String nome = sc.nextLine();
        
        // Valida se o ID da empresa é numérico
        if (!idEmpresa.matches("\\d+")) {
            System.out.println("Inválido.");
            return;
        }
        
        // Converte o ID da categoria de forma segura (se for numérico envia o Int, senão envia null)
        Integer catId = idCategoria.matches("\\d+") ? Integer.parseInt(idCategoria) : null;
        
        favoritoService.adicionar(u.getIdUsuario(), Integer.parseInt(idEmpresa), catId, nome);
        System.out.println("Favorito adicionado.");
    }

    // Listagem simples da tabela de favoritos do usuário
    public void listar() {
        UsuarioModel u = usuarioService.getUsuarioLogado();
        List<FavoritoModel> lista = favoritoService.listar(u.getIdUsuario());
        
        if (lista.isEmpty()) {
            System.out.println("Nenhum favorito.");
            return;
        }
        
        System.out.println("\n=== FAVORITOS ===");
        for (FavoritoModel f : lista) {
            System.out.println(f.getIdFavorito() + " - Empresa:" + f.getIdEmpresa() + " - " + f.getNomeFavorito());
        }
    }

    // Listagem detalhada trazendo dados da Empresa associada ao favorito
    public void listarEmpresasFavoritas() {
        UsuarioModel u = usuarioService.getUsuarioLogado();
        List<EmpresaModel> lista = favoritoService.listarEmpresasFavoritas(u.getIdUsuario());
        
        if (lista.isEmpty()) {
            System.out.println("Nenhuma empresa favorita.");
            return;
        }
        
        System.out.println("\n=== EMPRESAS FAVORITAS ===");
        for (EmpresaModel e : lista) {
            System.out.println(e.getIdEmpresa() + " - " + e.getNome() + " - " + e.getCnpj());
        }
    }

    // Fluxo de exclusão de um favorito
    public void remover() {
        listar(); // Exibe a lista para o usuário ver os IDs disponíveis
        
        System.out.print("ID Favorito: ");
        String e = sc.nextLine();
        
        if (!e.matches("\\d+")) {
            System.out.println("Inválido.");
            return;
        }
        
        favoritoService.remover(Integer.parseInt(e));
        System.out.println("Favorito removido.");
    }
}