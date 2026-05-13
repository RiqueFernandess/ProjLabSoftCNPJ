package service;

import model.EmpresaModel;
import repository.EmpresaRepository;
import util.CnpjValidator;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EmpresaService {
    private final EmpresaRepository repository;

    public EmpresaService() {
        this.repository = new EmpresaRepository();
    }

    public EmpresaModel consultarPorCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.isBlank() || !CnpjValidator.validar(cnpj)) return null;

        EmpresaModel empresaExistente = repository.buscarPorCnpj(cnpj);
        if (empresaExistente != null) return empresaExistente;

        EmpresaModel empresaApi = buscarNaApi(cnpj);
        if (empresaApi == null || !dadosValidos(empresaApi)) return null;

        repository.salvar(empresaApi);
        return empresaApi;
    }

    public List<EmpresaModel> listarEmpresas() {
        return repository.listar();
    }

    public void removerEmpresa(int idEmpresa) {
        repository.remover(idEmpresa);
    }

    public EmpresaModel buscarEmpresaPorCnpj(String cnpj) {
        return repository.buscarPorCnpj(cnpj);
    }

    public EmpresaModel obterUltimaConsulta() {
        return repository.ultimaConsulta();
    }

    private EmpresaModel buscarNaApi(String cnpj) {
        try {
            URL url = new URL("https://www.receitaws.com.br/v1/cnpj/" + cnpj);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            Scanner sc = new Scanner(conn.getInputStream(), "UTF-8");
            StringBuilder jsonBuilder = new StringBuilder();
            while (sc.hasNext()) jsonBuilder.append(sc.nextLine());
            sc.close();

            String json = jsonBuilder.toString();
            String nome = extrair(json, "\"nome\": \"");
            String situacao = extrair(json, "\"situacao\": \"");
            String tipoEmpresa = extrair(json, "\"tipo\": \"");
            String municipio = extrair(json, "\"municipio\": \"");
            String dataAberturaStr = extrair(json, "\"abertura\": \"");
            String dataSituacaoStr = extrair(json, "\"data_situacao\": \"");

            if (nome.equals("N/A") || nome.equals("null")) return null;

            LocalDate dataAbertura = parseDate(dataAberturaStr);
            LocalDate dataEncerramento = "BAIXADA".equalsIgnoreCase(situacao) ? parseDate(dataSituacaoStr) : null;

            return new EmpresaModel(cnpj, nome, tipoEmpresa, municipio, dataAbertura, dataEncerramento, situacao);
        } catch (Exception e) {
            System.out.println("Erro ao consultar API.");
            return null;
        }
    }

    private boolean dadosValidos(EmpresaModel e) {
        return e.getCnpj() != null && !e.getCnpj().isBlank() &&
               e.getNome() != null && !e.getNome().isBlank() &&
               e.getSituacao() != null && !e.getSituacao().isBlank();
    }

    private String extrair(String json, String chave) {
        try {
            int i = json.indexOf(chave) + chave.length();
            return json.substring(i, json.indexOf("\"", i));
        } catch (Exception e) {
            return "N/A";
        }
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.equals("N/A") || dateStr.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return null;
        }
    }
}