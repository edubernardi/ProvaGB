package com.company;

import java.util.ArrayList;

public class Coluna<T>{
    private String nomeColuna;
    private ArrayList<T> valores = new ArrayList<T>();
    private boolean isInt;

    public Coluna(String nomeColuna, boolean isInt) {
        this.nomeColuna = nomeColuna;
        this.isInt = isInt;
    }

    public void add(T valor){
        valores.add(valor);
    }

    public String toString(){
        String s = nomeColuna + ": ";
        for (T valor: valores){
            s += " " + valor;
        }
        return s;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public String paraArquivo(){
        String s = nomeColuna + ";";
        for (T valor: valores){
            s += valor + ";";
        }
        return s;
    }

    public boolean isInt(){
        return isInt;
    }
}
