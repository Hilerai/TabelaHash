package com.tde.hash;

public class TabelaHash1 extends TabelaHashBase {

    @Override
    protected int calcularHash(String chave) {
        long somaAscii = 0;

        for (int i = 0; i < chave.length(); i++) {
            somaAscii += (int) chave.charAt(i);
        }

        return (int) (somaAscii % CAPACIDADE);
    }
}
