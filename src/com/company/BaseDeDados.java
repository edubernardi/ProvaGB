package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class BaseDeDados {
    private String nome;
    private ArrayList<Tabela> tabelas;

    public void criarTabela(String nomeTabela, String lista) {
        for (Tabela t : tabelas) {
            if (t.getNome().equals(nomeTabela)) {
                return;
            }
        }

        String parametros = "";
        for (int i = 0; i < lista.length(); i++) {
            if (lista.charAt(i) == '(') {
                parametros = "";
                continue;
            }
            if (lista.charAt(i) == ')') {
                break;
            }
            parametros += lista.charAt(i);
        }

        Tabela novaTabela = new Tabela(nomeTabela);

        String nome = "";
        String tipo = "";
        boolean definindoNome = true;
        for (int i = 0; i < parametros.length(); i++) {
            if (parametros.charAt(i) == ',' || i == (parametros.length() - 1)) {
                if (i == (parametros.length() - 1)) {
                    tipo += parametros.charAt(i);
                }
                novaTabela.adicionarColuna(nome, tipo);
                nome = "";
                tipo = "";
                definindoNome = true;
            } else if (parametros.charAt(i) == ' ') {
                if (!nome.equals("")) {
                    definindoNome = false;
                }
            } else if (definindoNome) {
                nome += parametros.charAt(i);
            } else {
                tipo += parametros.charAt(i);
            }
        }
        tabelas.add(novaTabela);
    }

    public void inserir(String nome, String dados) {
        for (Tabela t : tabelas) {
            if (t.getNome().equals(nome)) {
                t.inserir(dados);
                return;
            }
        }
        System.out.println("ERRO: Tabela " + nome + " nao existe");
    }

    public BaseDeDados(String nome) {
        this.nome = nome;
        tabelas = new ArrayList<Tabela>();
    }

    public BaseDeDados(String nome, ArrayList<Tabela> tabelas) {
        this.nome = nome;
        this.tabelas = tabelas;
    }

    public String getNome() {
        return nome;
    }

    public String query(String listaColunas, String nomeTabela) {
        for (Tabela t : tabelas) {
            if (t.getNome().equals(nomeTabela)) {
                ArrayList<String> lista = new ArrayList<String>(Arrays.asList(listaColunas.split(",")));
                if (lista.size() == 1) {
                    if (lista.get(0).equals("*")) {
                        return t.toString();
                    } else {
                        return t.toString(lista.get(0));
                    }
                } else if (lista.size() > 1) {
                    String s = "";
                    for (String coluna : lista) {
                        s += t.toString(coluna);
                    }
                    return s;
                }
            }
        }
        System.out.println("ERRO: Tabela " + nomeTabela + " nao existe");
        return "";
    }

    public String paraArquivo() {
        String s = "";
        for (Tabela t : tabelas) {
            s += t.paraArquivo();
        }
        return s;
    }

    public void deletar(String nomeTabela, String parametro, String valor) {
        for (Tabela t : tabelas) {
            if (t.getNome().equals(nomeTabela)) {
                t.deletar(parametro, valor);
                return;
            }
        }
        System.out.println("ERRO: Tabela nao existente em tentativa de delecao");
    }

    public void deletar(String nomeTabela) {
        for (Tabela t : tabelas) {
            if (t.getNome().equals(nomeTabela)) {
                t.deletarTudo();
                return;
            }
        }
        System.out.println("ERRO: Tabela nao existente em tentativa de delecao");
    }
}

