import java.util.List;
import java.util.Scanner;

import repository.Repository;
import service.CnpjService;
import model.Empresa;

public class MainCnpj {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CnpjService service = new CnpjService();
        Repository<Empresa> repo = new Repository<>();

        String ultimoCNPJ = "";
        int num_de_consulta = 0;

        while (true) {

            Empresa ultimaConsulta = repo.ultimaConsulta();

            System.out.printf(
                "%n%n--NÚMERO DE CONSULTAS REALIZADAS: %d%n",
                num_de_consulta
            );

            if (ultimaConsulta != null) {

                System.out.println(
                    "--Última Consulta Feita: "
                    + ultimaConsulta.getNome()
                    + " - "
                    + ultimaConsulta.getCnpj()
                );
            }

            System.out.println("\n1 - Consultar CNPJ");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar empresa");
            System.out.println("4 - Remover empresa");
            System.out.println("0 - Sair");

            String entradaMenu = sc.nextLine();

            // VALIDA MENU
            if (!entradaMenu.matches("[0-4]")) {

                System.out.println("Opção inválida.");
                continue;
            }

            int op = Integer.parseInt(entradaMenu);

            System.out.println("");

            // CONSULTAR CNPJ

            if (op == 1) {

                if (num_de_consulta == 20) {

                    System.out.println("Limite de consultas atingido.");
                    continue;
                }

                System.out.print("Digite o CNPJ: ");

                String cnpj = sc.nextLine();

                String cnpjLimpo = cnpj.replaceAll("\\D", "");

                if (cnpjLimpo.isEmpty()) {

                    System.out.println("Insira um CNPJ.");
                    continue;
                }

                // VALIDA CNPJ
                if (!validarCnpj(cnpjLimpo)) {

                    System.out.println("CNPJ inválido.");
                    continue;
                }

                // BLOQUEIO DE CONSULTA REPETIDA
                if (ultimoCNPJ.equals(cnpjLimpo)) {

                    System.out.println("Consulta repetida.");
                    continue;
                }

                // CACHE INTELIGENTE
                Empresa empresaCache = repo.buscarPorCnpj(cnpjLimpo);

                if (empresaCache != null) {

                    System.out.println("Empresa já armazenada.");
                    System.out.println(empresaCache.getNome());

                    ultimoCNPJ = cnpjLimpo;
                    num_de_consulta++;

                    continue;
                }

                System.out.println("Consultando API...");

                Empresa e = service.buscar(cnpjLimpo);

                // CONTROLE DE QUALIDADE
                if (
                    e != null &&
                    !e.getCnpj().trim().isEmpty() &&
                    !e.getNome().trim().isEmpty() &&
                    !e.getSituacao().trim().isEmpty()
                ) {

                    repo.salvar(e);

                    System.out.println("Empresa salva!");
                    System.out.println(e.getNome());

                } else {

                    System.out.println("Informações incompletas.");
                    continue;
                }

                ultimoCNPJ = cnpjLimpo;
                num_de_consulta++;

            // LISTAR

            } else if (op == 2) {

                List<Empresa> lista = repo.listar();

                if (lista.isEmpty()) {

                    System.out.println("Histórico vazio.");
                    continue;
                }

                int contador = 1;

                for (int i = lista.size() - 1; i >= 0; i--) {

                    Empresa e = lista.get(i);

                    System.out.println(
                        contador
                        + " - "
                        + e.getCnpj()
                        + " - "
                        + e.getNome()
                    );

                    contador++;
                }

            // BUSCAR EMPRESA

            } else if (op == 3) {

                System.out.print("Digite o nome ou CNPJ: ");

                String busca = sc.nextLine().trim();

                String buscaCnpj = busca.replaceAll("\\D", "");

                boolean encontrado = false;

                for (Empresa e : repo.listar()) {

                    // BUSCA POR CNPJ
                    if (
                        buscaCnpj.length() >= 3 &&
                        e.getCnpj().contains(buscaCnpj)
                    ) {

                        System.out.println(
                            e.getCnpj()
                            + " - "
                            + e.getNome()
                        );

                        encontrado = true;
                    }

                    // BUSCA POR NOME
                    else if (
                        e.getNome()
                        .toLowerCase()
                        .contains(busca.toLowerCase())
                    ) {

                        System.out.println(
                            e.getCnpj()
                            + " - "
                            + e.getNome()
                        );

                        encontrado = true;
                    }
                }

                if (!encontrado) {

                    System.out.println("Nenhuma empresa encontrada.");
                }

            // REMOVER EMPRESA
            } else if (op == 4) {

                List<Empresa> lista = repo.listar();

                if (lista.isEmpty()) {

                    System.out.println("Histórico vazio.");
                    continue;
                }

                for (int i = 0; i < lista.size(); i++) {

                    Empresa e = lista.get(i);

                    System.out.println(
                        (i + 1)
                        + " - "
                        + e.getCnpj()
                        + " - "
                        + e.getNome()
                    );
                }

                System.out.print(
                    "Digite o número da empresa para remover: "
                );

                String entrada = sc.nextLine();

                if (!entrada.matches("\\d+")) {

                    System.out.println("Digite apenas números.");
                    continue;
                }

                int index = Integer.parseInt(entrada);

                if (index > 0 && index <= lista.size()) {

                    Empresa removida = lista.remove(index - 1);

                    System.out.println(
                        "Empresa removida: "
                        + removida.getNome()
                    );

                } else {

                    System.out.println("Número inválido.");
                }

            } else {

                break;
            }
        }

        sc.close();
    }

    // VALIDADOR DE CNPJ
    public static boolean validarCnpj(String cnpj) {

        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            return false;
        }

        // EVITA CNPJ COM TODOS NÚMEROS IGUAIS
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {

            int[] peso1 = {
                5,4,3,2,9,8,7,6,5,4,3,2
            };

            int[] peso2 = {
                6,5,4,3,2,9,8,7,6,5,4,3,2
            };

            int soma = 0;

            for (int i = 0; i < 12; i++) {

                soma +=
                    Character.getNumericValue(cnpj.charAt(i))
                    * peso1[i];
            }

            int resto = soma % 11;

            int digito1 =
                (resto < 2) ? 0 : 11 - resto;

            soma = 0;

            for (int i = 0; i < 13; i++) {

                soma +=
                    Character.getNumericValue(cnpj.charAt(i))
                    * peso2[i];
            }

            resto = soma % 11;

            int digito2 =
                (resto < 2) ? 0 : 11 - resto;

            return
                digito1 ==
                Character.getNumericValue(cnpj.charAt(12))
                &&
                digito2 ==
                Character.getNumericValue(cnpj.charAt(13));

        } catch (Exception e) {

            return false;
        }
    }
}