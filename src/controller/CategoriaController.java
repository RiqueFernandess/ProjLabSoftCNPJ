package controller;

import model.CategoriaModel;
import model.UsuarioModel;
import service.CategoriaService;
import service.UsuarioService;
import java.util.*;

public class CategoriaController {

    private final Scanner sc;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    // Construtor recebendo as dependências necessárias
    public CategoriaController(Scanner sc, CategoriaService categoriaService, UsuarioService usuarioService) {
        this.sc = sc;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    // Fluxo de cadastro interativo via console
    public void cadastrar() {
        UsuarioModel u = usuarioService.getUsuarioLogado();
        
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        
        System.out.print("Descrição: ");
        String descricao = sc.nextLine();
        
        categoriaService.cadastrar(u.getIdUsuario(), nome, descricao);
        System.out.println("Categoria cadastrada.");
    }

    // Fluxo de listagem formatada no console
    public void listar() {
        UsuarioModel u = usuarioService.getUsuarioLogado();
        List<CategoriaModel> lista = categoriaService.listar(u.getIdUsuario());
        
        if (lista.isEmpty()) {
            System.out.println("Nenhuma categoria.");
            return;
        }
        
        System.out.println("\n=== CATEGORIAS ===");
        for (CategoriaModel c : lista) {
            System.out.println(c.getIdCategoria() + " - " + c.getNome() + " - " + c.getDescricao());
        }
    }

    // Fluxo de remoção com validação de entrada numérica
    public void remover() {
        listar(); // Exibe a lista antes de pedir o ID
        
        System.out.print("ID Categoria: ");
        String e = sc.nextLine();
        
        // Valida se a entrada contém apenas dígitos
        if (!e.matches("\\d+")) {
            System.out.println("Inválido.");
            return;
        }
        
        categoriaService.remover(Integer.parseInt(e));
        System.out.println("Categoria removida.");
    }
}