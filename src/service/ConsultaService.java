package service;

public class ConsultaService {

    private int numeroConsultas;
    private String ultimoCnpj;

    private static final int LIMITE_CONSULTAS = 20;

    public ConsultaService() {
        this.numeroConsultas = 0;
        this.ultimoCnpj = "";
    }

    public boolean atingiuLimite() {
        return numeroConsultas >= LIMITE_CONSULTAS;
    }

    public boolean consultaRepetida(String cnpj) {
        return cnpj.equals(ultimoCnpj);
    }

    public void registrarConsulta(String cnpj) {
        ultimoCnpj = cnpj;
        numeroConsultas++;
    }

    public int getNumeroConsultas() {
        return numeroConsultas;
    }
}