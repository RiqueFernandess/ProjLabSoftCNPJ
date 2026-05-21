package service;

import model.CategoriaModel;
import repository.CategoriaRepository;
import java.time.LocalDateTime;
import java.util.*;

public class CategoriaService {

    private final CategoriaRepository repository;

    // Construtor inicializando o repositório
    public CategoriaService() {
        repository = new CategoriaRepository();
    }

    // Regra de negócio para cadastrar uma nova categoria
    public void cadastrar(Integer idUsuario, String nome, String descricao) {
        CategoriaModel c = new CategoriaModel();
        
        c.setIdUsuario(idUsuario);
        c.setNome(nome);
        c.setDescricao(descricao);
        c.setDataCriacao(LocalDateTime.now()); // Gera o timestamp atual
        
        repository.salvar(c);
    }

    // Listar categorias associadas ao usuário
    public List<CategoriaModel> listar(int idUsuario) {
        return repository.listar(idUsuario);
    }

    // Remover uma categoria pelo ID
    public void remover(int idCategoria) {
        repository.remover(idCategoria);
    }
}