package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class Coluna<T> {
    private String nomeColuna;
    private ArrayList<T> valores = new ArrayList<T>();
    private boolean isInt;

    public Coluna(String nomeColuna, boolean isInt) {
        this.nomeColuna = nomeColuna;
        this.isInt = isInt;
    }

    public void add(T valor) {
        valores.add(valor);
    }

    public String toString() {
        String s = nomeColuna + ": ";
        for (T valor : valores) {
            s += " " + valor;
        }
        return s;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public String paraArquivo() {
        String s = nomeColuna + ";";
        for (T valor : valores) {
            s += valor + ";";
        }
        return s;
    }

    public boolean isInt() {
        return isInt;
    }

    public LinkedList<Integer> deletar(T valor) {
        LinkedList<Integer> indexes = new LinkedList<Integer>();
        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i).equals(valor)) {
                indexes.add(i);
                valores.remove(i);
                i--;
            }
        }

        for (T v : valores) {
            if (v.equals(valor)) {
                indexes.add(valores.indexOf(v));
                valores.remove(v);
            }
        }
        return indexes;
    }

    public void deletarIndice(int i) {
        valores.remove(i);
    }

    public void deletarTudo() {
        valores.clear();
    }
}
