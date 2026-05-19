package service;

import model.ConsultaModel;
import model.EmpresaModel;
import repository.ConsultaRepository;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private int numeroConsultas;
    private String ultimoCnpj;
    private static final int LIMITE_CONSULTAS = 20;
    private final ConsultaRepository repository;

    public ConsultaService() {
        this.numeroConsultas = 0;
        this.ultimoCnpj = "";
        this.repository = new ConsultaRepository();
    }

    public boolean atingiuLimite() {
        return numeroConsultas >= LIMITE_CONSULTAS;
    }

    public boolean consultaRepetida(String cnpj) {
        return cnpj.equals(ultimoCnpj);
    }

    public void registrarConsulta(String cnpj) {
        this.ultimoCnpj = cnpj;
        this.numeroConsultas++;
    }

    public void registrarConsulta(Integer idUsuario, Integer idEmpresa, boolean sucesso, String msgErro, int tempoRespostaMs) {
        ConsultaModel consulta = new ConsultaModel();
        consulta.setIdUsuario(idUsuario);
        consulta.setIdEmpresa(idEmpresa);
        consulta.setDataHora(LocalDateTime.now());
        consulta.setSucesso(sucesso);
        consulta.setMsgErro(msgErro);
        consulta.setTempoRespostaMs(tempoRespostaMs);

        repository.salvar(consulta);
    }

    public List<ConsultaModel> listarUltimasPorUsuario(int idUsuario) {
        return repository.listarUltimasPorUsuario(idUsuario);
    }

    public List<ConsultaModel> listarComErro(int idUsuario) {
        return repository.listarComErro(idUsuario);
    }

    public List<EmpresaModel> listarEmpresasConsultadas(int idUsuario) {
        return repository.listarEmpresasConsultadas(idUsuario);
    }

    public int getNumeroConsultas() {
        return numeroConsultas;
    }
}