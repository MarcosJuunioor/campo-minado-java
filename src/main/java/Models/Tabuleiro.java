/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author marco
 */
public class Tabuleiro {

    private final HashMap<String, Celula> celulas;
    private final DimensoesTabuleiro dt;
    private int numBombas;
    private int bombasRestantes;

    public Tabuleiro(DimensoesTabuleiro dt) {
        this.celulas = new HashMap<String, Celula>();
        this.dt = dt;
        this.criarTabuleiro();
        this.bombasRestantes = this.numBombas;
    }

    private void criarTabuleiro() {

        int numLinhas = getDt().getNumLinhas();
        int numColunas = getDt().getNumColunas();

        switch (numColunas) {
            case 9:
                this.numBombas = 10;
                break;
            case 16:
                this.numBombas = 40;
                break;
            case 40:
                this.numBombas = 99;
                break;

        }
        HashMap<String, String> celulasComBombas = new HashMap<String, String>();

        Random rand = new Random();
        String celulaComBomba;
        int linhaBomba;
        int colunaBomba;

        //Define as células com bombas
        for (int i = 0; i < this.getNumBombas(); i++) {
            do {
                linhaBomba = rand.nextInt(numLinhas) + 1;
                colunaBomba = rand.nextInt(numColunas) + 1;
                celulaComBomba = "l" + linhaBomba + "c" + colunaBomba;
            } while (celulasComBombas.containsKey(celulaComBomba));

            celulasComBombas.put(celulaComBomba, celulaComBomba);
        }

        //Cria as células
        for (int l = 1; l <= numLinhas; l++) {
            String linha = "l" + l;
            for (int c = 1; c <= numColunas; c++) {
                String coluna = "c" + c;
                if (celulasComBombas.containsKey(linha + coluna)) {
                    this.getCelulas().put(linha + coluna, new Celula(linha, coluna, ConteudoCelula.BOMBA));
                } else {
                    this.getCelulas().put(linha + coluna, new Celula(linha, coluna, ConteudoCelula.VAZIA));
                }

            }
        }
        this.definirCelulasComNumeros();

    }

    public void definirCelulasComNumeros() {
        int numLinhas = getDt().getNumLinhas();
        int numColunas = getDt().getNumColunas();

        for (int l = 1; l <= numLinhas; l++) {
            String linha = "l" + l;
            for (int c = 1; c <= numColunas; c++) {
                int contadorNumBombas = 0;
                String coluna = "c" + c;
                String endCelula = linha + coluna;

                Celula celAtual = this.celulas.get(endCelula);
                if (!celAtual.getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                    //Condição para verificar se há bombas ao redor da célula
                    if ((c > 1 && c < numColunas) && (l > 1 && l < numLinhas)) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
                        String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);
                        String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);
                        String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(diagonalSuperiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }
                    } else if (c == 1 && l == 1) {
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);

                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }

                    } else if (c == numColunas && l == 1) {
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }

                    } else if (c == 1 && l > 1 && l < numLinhas) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
                        String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }

                    } else if (c == numColunas && l > 1 && l < numLinhas) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);
                        String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }
                    } else if (l == numLinhas && c == 1) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorDireita)) {
                            contadorNumBombas++;
                        }

                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }
                    } else if (l == numLinhas && c == numColunas) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }
                    } else if (l == 1 && c > 1 && c < numColunas) {
                        String embaixo = "l" + (l + 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);
                        String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

                        if (this.temBomba(embaixo)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(diagonalInferiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalInferiorEsquerda)) {
                            contadorNumBombas++;
                        }
                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }

                    } else if (l == numLinhas && c > 1 && c < numColunas) {
                        String emCima = "l" + (l - 1) + "c" + c;
                        String direita = "l" + l + "c" + (c + 1);
                        String esquerda = "l" + l + "c" + (c - 1);
                        String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
                        String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);

                        if (this.temBomba(emCima)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(direita)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(esquerda)) {
                            contadorNumBombas++;
                        }

                        if (this.temBomba(diagonalSuperiorDireita)) {
                            contadorNumBombas++;
                        }
                        if (this.temBomba(diagonalSuperiorEsquerda)) {
                            contadorNumBombas++;
                        }

                        if (contadorNumBombas != 0) {
                            celAtual.setConteudoCelula(this.getNumeroBombasEnum(contadorNumBombas));
                        }
                    }
                }
            }
        }
    }

    public ConteudoCelula getNumeroBombasEnum(int contadorNumBombas) {
        switch (contadorNumBombas) {
            case 1:
                return ConteudoCelula.UM;
            case 2:
                return ConteudoCelula.DOIS;
            case 3:
                return ConteudoCelula.TRES;
            case 4:
                return ConteudoCelula.QUATRO;
            case 5:
                return ConteudoCelula.CINCO;
            case 6:
                return ConteudoCelula.SEIS;
            case 7:
                return ConteudoCelula.SETE;
            case 8:
                return ConteudoCelula.OITO;
            default:
                break;
        }
        return ConteudoCelula.ZERO;
    }

    public boolean temBomba(String posicao) {
        return this.celulas.get(posicao).getConteudoCelula().equals(ConteudoCelula.BOMBA);
    }

    /**
     * @return the celulas
     */
    public HashMap<String, Celula> getCelulas() {
        return celulas;
    }

    /**
     * @return the dt
     */
    public DimensoesTabuleiro getDt() {
        return dt;
    }

    /**
     * @return the numBombas
     */
    public int getNumBombas() {
        return numBombas;
    }

    /**
     * @return the bombasRestantes
     */
    public int getBombasRestantes() {
        return bombasRestantes;
    }

    /**
     * @param bombasRestantes the bombasRestantes to set
     */
    public void setBombasRestantes(int bombasRestantes) {
        this.bombasRestantes = bombasRestantes;
    }
}
