package com.company;

public class Main {

    public static void main(String[] args) {
        SQLServer ss = new SQLServer();

        ss.connect("cadastro"); //abre base de dados; cria ela, caso não exista...
        ss.execute("create table cidades (nome text, uf text, cod int)");
        ss.execute("insert into cidades values('poa', 'RS', 100)");
        ss.execute("insert into cidades values('sampa', 'SP', 200)");
        ss.execute("insert into cidades values('canoas', 'RS', 300)");
        ss.execute("create table pessoas (nome text, idade int)");
        ss.execute("insert into pessoas values('carlos',21)");
        ss.execute("insert into pessoas values('adriana',22)");
        ss.execute("insert into pessoas values('laura',34)");
        String result = ss.query("select * from pessoas");
        System.out.println(result);
        ss.execute("delete from cidades where cod = 200"); //remove sampa...
        System.out.println(ss.query("select nome, cod from cidades"));
        ss.execute("delete from pessoas"); //elimina todas linhas da tabela pessoas
        ss.close(); //fecha conexao com base de dados

    }
}

