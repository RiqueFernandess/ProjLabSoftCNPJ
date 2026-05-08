package repository;

import java.util.ArrayList;
import java.util.List;
import model.Empresa;

public class Repository<T extends Empresa> {

    private List<T> lista = new ArrayList<>();

    public void salvar(T obj) {
        if(lista.size() >= 15) {
            lista.remove(0);
        }

        lista.add(obj);
    }

    public T ultimaConsulta() {
        if(lista.isEmpty()) {
            return null;
        }

        return lista.get(lista.size() -1);
    }

    public T buscarPorCnpj(String cnpj) {
        for (T e : lista) {
            if (e.getCnpj().equals(cnpj)) {
                return e;
            }
        }
        return null;
    }

    public List<T> listar() {
        return lista;
    }
}