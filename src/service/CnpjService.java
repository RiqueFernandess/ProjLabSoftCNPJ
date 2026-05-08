package service;

import model.Empresa;
import java.net.*;
import java.util.Scanner;

public class CnpjService {

    public Empresa buscar(String cnpj) {

        try {
            URL url = new URL("https://www.receitaws.com.br/v1/cnpj/" + cnpj);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int codResposta = conn.getResponseCode();

            if(codResposta == 429) {
                System.out.println("Limite de consultas atigindo, aguarde 1 minuto");
                return null;
            }

            if(codResposta != 200) {
                System.out.println("Erro na consulta. Código HTTP: " + codResposta);
            }

            Scanner sc = new Scanner(conn.getInputStream(), "UTF-8");
            String json = "";

            while (sc.hasNext()) json += sc.nextLine();
            sc.close();

            if(json.isEmpty()) {
                System.out.println("Sem resposta da API");
                return null;
            }

            String nome = extrair(json, "\"nome\": \"");
            String situacao = extrair(json, "\"situacao\": \"");

            return new Empresa(cnpj, nome, situacao);

        } catch (Exception e) {
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
}