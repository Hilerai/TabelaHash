package com.tde.hash;

public class TabelaHash2 extends TabelaHashBase {

    @Override
    protected int calcularHash(String chave) {
        long somaBlocos = 0;
        int blocoSize = 4;
        
        char[] caracteres = chave.toCharArray();
        
        for (int i = 0; i < caracteres.length; i += blocoSize) {
            long blocoValor = 0;
            
            for (int j = 0; j < blocoSize; j++) {
                if (i + j < caracteres.length) {
                    blocoValor = (blocoValor * 31) + (int) caracteres[i + j];
                }
            }
            somaBlocos += blocoValor;
        }

        return (int) (somaBlocos % CAPACIDADE);
    }
}