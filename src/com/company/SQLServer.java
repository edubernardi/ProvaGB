package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SQLServer {
    private BaseDeDados baseConectada;

    public void connect(String nomeBaseDados) {
        try (BufferedReader in = new BufferedReader(new FileReader(nomeBaseDados + ".db"))) {
            ArrayList<Tabela> tabelas = new ArrayList<Tabela>();
            String linha;
            while ((linha = in.readLine()) != null) {
                ArrayList<String> valores = new ArrayList<String>(Arrays.asList(linha.split(";")));

                String nomeTabela = valores.get(0);
                String nomeColuna = valores.get(1);

                boolean isInt = false;
                if (!valores.isEmpty()){
                    if (!(valores.get(2).charAt(0) == '\'')){
                        isInt = true;
                    }
                }

                Coluna c = new Coluna(nomeColuna, isInt);

                for (int i = 2; i < valores.size(); i++) {
                    c.add(valores.get(i));
                }

                boolean found = false;
                for (Tabela t : tabelas) {
                    if (t.getNome().equals(nomeTabela)) {
                        t.adicionarColuna(c);
                        found = true;
                    }
                }
                if (!found) {
                    tabelas.add(new Tabela(nomeTabela));
                    tabelas.get(tabelas.size() - 1).adicionarColuna(c);
                }
            }
            baseConectada = new BaseDeDados(nomeBaseDados, tabelas);
        } catch (Exception e) {
            try (PrintWriter out = new PrintWriter(new FileWriter(nomeBaseDados + ".db"))) {
                baseConectada = new BaseDeDados(nomeBaseDados);
            } catch (Exception f) {

            }
        }
    }

    public void execute(String sqlStatement) {
        try {
            String comandos = "";
            String valores = "";
            for (int i = 0; i < sqlStatement.length(); i++) {
                if (sqlStatement.charAt(i) == '(') {
                    break;
                }
                comandos += sqlStatement.charAt(i);
                valores = sqlStatement.substring(i);
            }

            ArrayList<String> listaComandos = new ArrayList<String>(Arrays.asList(comandos.split(" ")));

            switch (listaComandos.get(0)) {
                case ("create"):
                    if (listaComandos.get(1).equals("table")) {
                        baseConectada.criarTabela(listaComandos.get(2), valores);
                    }
                    break;
                case ("insert"):
                    if (listaComandos.get(1).equals("into")) {
                        baseConectada.inserir(listaComandos.get(2), valores);
                    }
                    break;
                case ("delete"):
                    System.out.println("delete");
                    break;
            }
        } catch (Exception e){
            System.out.println("Erro de sintaxe em \"" + sqlStatement + "\"");
        }


    }

    public String query(String sqlQuery) {
        try {
            String s = "";
            for (int i = 0; i < sqlQuery.length(); i++) {
                if (s.length() > 0 && s.charAt(s.length() - 1) == ',' && sqlQuery.charAt(i) == ' ') {
                    s += sqlQuery.charAt(i + 1);
                    i++;
                } else {
                    s += sqlQuery.charAt(i);
                }
            }
            ArrayList<String> query = new ArrayList<String>(Arrays.asList(s.split(" ")));
            if (query.get(0).equals("select")) {
                if (query.get(2).equals("from")) {
                    return "(Query: \"" + sqlQuery + "\")\n" + baseConectada.query(query.get(1), query.get(3));
                }
            }
            System.out.println("Erro de sintaxe em \"" + sqlQuery + "\"");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro de sintaxe em \"" + sqlQuery + "\"");
            return "";
        }
    }

    public void close() {
        if (baseConectada != null){
            try (PrintWriter out = new PrintWriter(new FileWriter(baseConectada.getNome() + ".db"))) {
                out.print(baseConectada.paraArquivo());
            } catch (IOException e) {
            }
        } else {
            System.out.println("ERRO: Nenhuma base conectada para fechar a conexao");
        }
    }
}

