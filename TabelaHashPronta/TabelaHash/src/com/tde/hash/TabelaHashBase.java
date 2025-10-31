package com.tde.hash;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class No {
    String nome;
    No proximo;

    public No(String nome) {
        this.nome = nome;
        this.proximo = null;
    }
}

public abstract class TabelaHashBase {
    protected static final int CAPACIDADE = 32;
    
    protected No[] tabela;
    
    protected long colisoes;
    
    protected int[] distribuicao;

    public TabelaHashBase() {
        this.tabela = new No[CAPACIDADE];
        this.colisoes = 0;
        this.distribuicao = new int[CAPACIDADE];
    }

    protected abstract int calcularHash(String chave);

    public void inserir(String chave) {
        int indice = calcularHash(chave);

        if (tabela[indice] == null) {
            tabela[indice] = new No(chave);
        } else {
            colisoes++;
            No novoNo = new No(chave);
            novoNo.proximo = tabela[indice];
            tabela[indice] = novoNo;
        }

        distribuicao[indice]++;
    }

    public boolean buscar(String chave) {
        int indice = calcularHash(chave);
        
        No atual = tabela[indice];
        while (atual != null) {
            if (compararStrings(atual.nome, chave)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    private boolean compararStrings(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return s1 == s2;
        }
        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public long getColisoes() {
        return colisoes;
    }

    public int[] getDistribuicao() {
        return distribuicao;
    }
    
    public int getCapacidade() {
        return CAPACIDADE;
    }
}
