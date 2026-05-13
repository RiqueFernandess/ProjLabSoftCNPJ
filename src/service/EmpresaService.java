package service;

import model.EmpresaModel;
import util.CnpjValidator;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpresaService {

    public EmpresaModel buscar(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");

        //REGRA 5
        if (!CnpjValidator.validar(cnpj)) {
            System.out.println("CNPJ inválido.");
            return null;
        }

        try {
            URL url = new URL("https://www.receitaws.com.br/v1/cnpj/" + cnpj);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            Scanner sc = new Scanner(conn.getInputStream(), "UTF-8");

            String json = "";
            while (sc.hasNext()) {
                json += sc.nextLine();
            }

            sc.close();

            String nome = extrair(json, "\"nome\": \"");
            String situacao = extrair(json, "\"situacao\": \"");
            String tipoEmpresa = extrair(json, "\"tipo\": \"");
            String municipio = extrair(json, "\"municipio\": \"");
            String dataAberturaStr = extrair(json, "\"abertura\": \"");
            
            // "data_situacao" na ReceitaWS costuma indicar a data da baixa se a situação for BAIXADA.
            String dataSituacaoStr = extrair(json, "\"data_situacao\": \"");

            // REGRA B
            if (nome.equals("N/A") || nome.equals("null")) {
                return null;
            }

            LocalDate dataAbertura = parseDate(dataAberturaStr);
            LocalDate dataEncerramento = null;
            
            if ("BAIXADA".equalsIgnoreCase(situacao)) {
                dataEncerramento = parseDate(dataSituacaoStr);
            }

            return new EmpresaModel(cnpj, nome, tipoEmpresa, municipio, dataAbertura, dataEncerramento, situacao);//Revisar construtor

        } catch (Exception e) {
            System.out.println("Erro ao consultar API.");
            return null;
        }
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