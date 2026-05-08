package util;

public class CnpjValidator {

    public static boolean validar(String cnpj) {

        cnpj = cnpj.replaceAll("[^0-9]", "");//remove o que não for número

        if (cnpj.length() != 14){//verifica tamanho != de 14 digitos
            return false;
        }
        if (cnpj.matches("(\\d)\\1{13}")){//evita sequencias repetidas
            return false;
        }

        try {
            int soma = 0;
            int peso = 2;

            for (int i = 11; i >= 0; i--) {//percorre os 12 primeiros numeros do CNPJ
                soma += (cnpj.charAt(i) - '0') * peso;//número da posição atual * peso
                peso++;
                if (peso == 10){
                    peso = 2;
                }
            }

            int dig1 = soma % 11;// resto de divisão por 11
            dig1 = (dig1 < 2) ? 0 : 11 - dig1;//regra oficial, se resto < 2, digito  = 0, senão = 11 - resto
            soma = 0;
            peso = 2;

            for (int i = 12; i >= 0; i--) {//percorre 13 posições
                soma += (cnpj.charAt(i) - '0') * peso;
                peso++;
                if (peso == 10){
                    peso = 2;
                }
            }

            int dig2 = soma % 11;
            dig2 = (dig2 < 2) ? 0 : 11 - dig2;//aplica regra ao segundo dígito

            //compara dig1 com posição 12 e dig2 com posição 13. Se diferentes, retorna false
            return dig1 == (cnpj.charAt(12) - '0') && dig2 == (cnpj.charAt(13) - '0');

        } catch (Exception e) {
            return false;
        }
    }
}