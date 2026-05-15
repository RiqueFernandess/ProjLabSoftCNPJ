package model;

import java.time.LocalDate;

public class ConsultaModel {

    private Integer idConsulta;
    private Integer idUsuario;
    private Integer idEmpresa;
    private LocalDate dataHora;
    private boolean sucesso;
    private String msgErro;
    private int tempoRespostaMs;

    public ConsultaModel(){}

    public ConsultaModel(Integer idUsuario, Integer idEmpresa, LocalDate dataHora, Boolean sucesso, String msgErro, Integer tempoRespostaMs) {
       this.idUsuario = idUsuario;
       this.idEmpresa = idEmpresa;
       this.dataHora = dataHora;
       this.sucesso = sucesso;
       this.msgErro = msgErro;
       this.tempoRespostaMs = tempoRespostaMs;
    }
    
    public ConsultaModel(Integer idConsulta, Integer idUsuario, Integer idEmpresa, LocalDate dataHora, Boolean sucesso, String msgErro, Integer tempoRespostaMs) {
       this.idConsulta = idConsulta;
       this.idUsuario = idUsuario;
       this.idEmpresa = idEmpresa;
       this.dataHora = dataHora;
       this.sucesso = sucesso;
       this.msgErro = msgErro;
       this.tempoRespostaMs = tempoRespostaMs;
    }

    public Integer getIdConsulta() { return idConsulta; }
    public Integer getIdUsuario() { return idUsuario; }
    public Integer getIdEmpresa() { return idEmpresa; }
    public LocalDate getDataHora() { return dataHora; }
    public Boolean isSucesso() { return sucesso; }
    public String getMsgErro() { return msgErro; }
    public Integer getTempoRespostaMs() { return tempoRespostaMs; }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

    public void setTempoRespostaMs(Integer tempoRespostaMs) {
        this.tempoRespostaMs = tempoRespostaMs;
    }

    

}
