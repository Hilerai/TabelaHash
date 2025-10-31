package com.tde.hash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    
    private static final String ARQUIVO_NOMES = "female_names.txt";
    private static final int NUMERO_BUSCAS = 1000; 
    private static final int TAMANHO_INICIAL_ARRAY = 1000; 

    
    private static String[] nomes;
    private static int totalNomes;

    
    private static void lerArquivo() {
        nomes = new String[TAMANHO_INICIAL_ARRAY];
        totalNomes = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_NOMES))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String nome = linha.trim();
                if (nome.length() > 0) {
                    if (totalNomes == nomes.length) {
                        int novoTamanho = nomes.length * 2;
                        String[] novoArray = new String[novoTamanho];
                        
                        for (int i = 0; i < nomes.length; i++) {
                            novoArray[i] = nomes[i];
                        }
                        
                        nomes = novoArray;
                    }
                    
                    nomes[totalNomes] = nome;
                    totalNomes++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + ARQUIVO_NOMES);
            e.printStackTrace();
        }
    }

    private static long medirInsercao(TabelaHashBase tabela) {
        long inicio = System.nanoTime();
        for (int i = 0; i < totalNomes; i++) {
            tabela.inserir(nomes[i]);
        }
        long fim = System.nanoTime();
        return fim - inicio;
    }
    
    private static long medirBusca(TabelaHashBase tabela) {
        long inicio = System.nanoTime();
        
        for (int i = 0; i < NUMERO_BUSCAS; i++) {
            if (i < totalNomes) {
                tabela.buscar(nomes[i]);
            } else {
                break;
            }
        }

        long fim = System.nanoTime();
        return fim - inicio;
    }

    private static void imprimirRelatorio(TabelaHashBase tabela, String nomeFuncao, long tempoInsercao, long tempoBusca) {
        System.out.println("==================================================");
        System.out.println("RELATÓRIO DA TABELA HASH: " + nomeFuncao);
        System.out.println("==================================================");
        
        System.out.println("1. Número de Colisões: " + tabela.getColisoes());
        
        double tempoInsercaoMs = (double) tempoInsercao / 1_000_000.0;
        double tempoBuscaMs = (double) tempoBusca / 1_000_000.0;
        System.out.printf("2. Tempo Total de Inserção: %.4f ms%n", tempoInsercaoMs);
        System.out.printf("3. Tempo Total de Busca (%d nomes): %.4f ms%n", NUMERO_BUSCAS, tempoBuscaMs);

        System.out.println("4. Distribuição das Chaves por Posição (Clusterização):");
        int[] distribuicao = tabela.getDistribuicao();

        System.out.println("+---------+-----------------+");
        System.out.println("| Posição | Chaves Inseridas|");
        System.out.println("+---------+-----------------+");

        for (int i = 0; i < tabela.getCapacidade(); i++) {
            System.out.printf("| %-7d | %-15d |%n", i, distribuicao[i]);
        }
        System.out.println("+---------+-----------------+");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("Iniciando leitura do arquivo de nomes...");
        lerArquivo();
        System.out.println("Leitura concluída. Total de nomes lidos: " + totalNomes);
        System.out.println();

        TabelaHash1 tabela1 = new TabelaHash1();
        System.out.println("Iniciando inserção na Tabela Hash 1 (Soma ASCII)...");
        long tempoInsercao1 = medirInsercao(tabela1);
        System.out.println("Inserção Tabela 1 concluída.");

        System.out.println("Iniciando busca em " + NUMERO_BUSCAS + " nomes na Tabela Hash 1...");
        long tempoBusca1 = medirBusca(tabela1);
        System.out.println("Busca Tabela 1 concluída.");

        TabelaHash2 tabela2 = new TabelaHash2();
        System.out.println("Iniciando inserção na Tabela Hash 2 (Dobramento)...");
        long tempoInsercao2 = medirInsercao(tabela2);
        System.out.println("Inserção Tabela 2 concluída.");

        System.out.println("Iniciando busca em " + NUMERO_BUSCAS + " nomes na Tabela Hash 2...");
        long tempoBusca2 = medirBusca(tabela2);
        System.out.println("Busca Tabela 2 concluída.");
        System.out.println();

        System.out.println("==================================================");
        System.out.println("RELATÓRIO COMPARATIVO FINAL");
        System.out.println("CAPACIDADE DA TABELA: " + TabelaHashBase.CAPACIDADE);
        System.out.println("TOTAL DE ELEMENTOS: " + totalNomes);
        System.out.println("==================================================");
        
        imprimirRelatorio(tabela1, "Função Hash 1 (Soma ASCII)", tempoInsercao1, tempoBusca1);
        imprimirRelatorio(tabela2, "Função Hash 2 (Dobramento)", tempoInsercao2, tempoBusca2);
    }
}
