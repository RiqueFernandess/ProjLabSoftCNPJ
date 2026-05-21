package service;

import model.EmpresaModel;
import model.FavoritoModel;
import repository.FavoritoRepository;
import java.time.LocalDateTime;
import java.util.*;

public class FavoritoService {

    private final FavoritoRepository repository;

    // Construtor inicializando o repositório de favoritos
    public FavoritoService() {
        repository = new FavoritoRepository();
    }

    // Regra de negócio para adicionar um novo favorito
    public void adicionar(Integer idUsuario, Integer idEmpresa, Integer idCategoria, String nomeFavorito) {
        FavoritoModel f = new FavoritoModel();
        
        f.setIdUsuario(idUsuario);
        f.setIdEmpresa(idEmpresa);
        f.setIdCategoria(idCategoria);
        f.setNomeFavorito(nomeFavorito);
        f.setDataCriacao(LocalDateTime.now()); // Define o momento exato da criação
        
        repository.salvar(f);
    }

    // Lista os registros da tabela de favoritos por usuário
    public List<FavoritoModel> listar(int idUsuario) {
        return repository.listar(idUsuario);
    }

    // Retorna a lista detalhada das empresas favoritadas pelo usuário (tabela empresa)
    public List<EmpresaModel> listarEmpresasFavoritas(int idUsuario) {
        return repository.listarEmpresasFavoritas(idUsuario);
    }

    // Remove o vínculo de favorito pelo ID
    public void remover(int idFavorito) {
        repository.remover(idFavorito);
    }
}