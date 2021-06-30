package com.company;

import java.util.ArrayList;

public class Tabela {
    private String nome;
    private ArrayList<Coluna> colunas = new ArrayList<Coluna>();


    public Tabela(String nome) {
        this.nome = nome;
    }

    public void adicionarColuna(String nome, String tipo){
        if (tipo.equals("int")){
            colunas.add(new Coluna<Integer>(nome, true));
        } else {
            colunas.add(new Coluna<String>(nome, false));
        }
    }

    public void adicionarColuna(Coluna c){
        colunas.add(c);
    }

    public void inserir(String dados){
        int j = 0;
        String valor = "";
        boolean inteiro = true;
        for (int i = 0; i < dados.length(); i++){
            if (dados.charAt(i) == '\''){
                if (inteiro){
                    valor = "";
                }
                inteiro = false;
            }
            if (dados.charAt(i) == '('){
                valor = "";
            } else if (dados.charAt(i) == ',' || dados.charAt(i) == ')'){
                if (inteiro){
                    if (colunas.get(j).isInt()){
                        colunas.get(j).add(Integer.parseInt(valor));
                    } else {
                        System.out.println("ERRO: Tipo Int nao eh suportado pela coluna " +
                                colunas.get(j).getNomeColuna() + " na tabela " + nome);
                    }
                } else {
                    if (!colunas.get(j).isInt()){
                        colunas.get(j).add(valor);
                    } else {
                        System.out.println("ERRO: Tipo String nao eh suportado pela coluna " +
                                colunas.get(j).getNomeColuna() + " na tabela " + nome);
                    }
                }
                valor = "";
                inteiro = true;
                j++;
            } else if (dados.charAt(i) != ' '){
                valor += dados.charAt(i);
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public String toString(){
        String s = "";
        for (Coluna c: colunas){
            s += c.toString() + "\n";
        }
        return s;
    }

    public String toString(String nomeColuna){
        for (Coluna c: colunas){
            if (c.getNomeColuna().equals(nomeColuna)){
                return c.toString() + "\n";
            }
        }
        System.out.println("ERRO: Coluna " + nomeColuna + " nao existe na tabela " + nome);
        return "";
    }

    public String paraArquivo(){
        String s = "";
        for (Coluna c: colunas){
            s += nome + ";" + c.paraArquivo() + "\n";
        }
        return s;
    }
}

