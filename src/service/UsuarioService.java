package service;

import model.UsuarioModel;
import repository.UsuarioRepository;

import java.time.LocalDate;

public class UsuarioService {

    private final UsuarioRepository repository;

    private UsuarioModel usuarioLogado;

    public UsuarioService() {
        repository = new UsuarioRepository();
    }

    public void cadastrar(
            String nome,
            String cpf,
            String email,
            String senha
    ) {

        UsuarioModel existente =
                repository.buscarPorEmail(email);

        if (existente != null) {

            throw new RuntimeException(
                    "Email já cadastrado"
            );
        }

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);

        // futuramente hash
        usuario.setSenha(senha);

        usuario.setDataCriacao(LocalDate.now());

        usuario.setAtivo(true);

        repository.salvar(usuario);
    }

    public boolean login(
            String email,
            String senha
    ) {

        UsuarioModel usuario =
                repository.buscarPorEmail(email);

        if (usuario == null) {
            return false;
        }

        if (!usuario.getAtivo()) {
            return false;
        }

        if (!usuario.getSenha().equals(senha)) {
            return false;
        }

        usuarioLogado = usuario;

        return true;
    }

    public void logout() {
        usuarioLogado = null;
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
    }

    public UsuarioModel getUsuarioLogado() {
        return usuarioLogado;
    }
}