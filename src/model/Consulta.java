package model;

import java.time.LocalDate;

public class Consulta {

    private int idConsulta;
    private int idUsuario;
    private int idEmpresa;
    private LocalDate dataHora;
    private boolean sucesso;
    private String msgErro;
    private int tempoRespostaMs;

    public Consulta(int idConsulta, int idUsuario, int idEmpresa, LocalDate dataHora, boolean sucesso, String msgErro, int tempoRespostaMs) {
        this.idConsulta = idConsulta;
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.dataHora = dataHora;
        this.sucesso = sucesso;
        this.msgErro = msgErro;
        this.tempoRespostaMs = tempoRespostaMs;
    }

    public int getIdConsulta() { return idConsulta; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdEmpresa() { return idEmpresa; }
    public LocalDate getDataHora() { return dataHora; }
    public boolean isSucesso() { return sucesso; }
    public String getMsgErro() { return msgErro; }
    public int getTempoRespostaMs() { return tempoRespostaMs; }

}
