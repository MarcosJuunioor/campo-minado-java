/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.HashMap;

/**
 *
 * @author marco
 */
public class Jogo {

    private boolean fimDoJogo;
    private boolean ganhou;
    private Tabuleiro tabuleiro;

    public Jogo(DimensoesTabuleiro dt) {
        this.fimDoJogo = false;
        this.ganhou = false;
        this.tabuleiro = new Tabuleiro(dt);
    }

    public boolean verificarSeGanhou() {
        HashMap<String, Celula> celulas = this.tabuleiro.getCelulas();
        int numLinhas = this.tabuleiro.getDt().getNumLinhas();
        int numColunas = this.tabuleiro.getDt().getNumColunas();
        
        for (int l = 1; l <= numLinhas; l++) {
            String linha = "l" + l;
            for (int c = 1; c <= numColunas; c++) {
                String coluna = "c" + c;
                Celula cel = celulas.get(linha+coluna);
                if(!cel.isRevelada() && !cel.getConteudoCelula().equals(ConteudoCelula.BOMBA)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean getGanhou(){
        return this.ganhou;
    }
    
    public void setGanhou(boolean ganhou) {
        this.ganhou = ganhou;
    }

    /**
     * @return the tabuleiro
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * @param tabuleiro the tabuleiro to set
     */
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    /**
     * @return the fimDoJogo
     */
    public boolean isFimDoJogo() {
        return fimDoJogo;
    }

    /**
     * @param fimDoJogo the fimDoJogo to set
     */
    public void setFimDoJogo(boolean fimDoJogo) {
        this.fimDoJogo = fimDoJogo;
    }

    public void revelarCelula(String enderecoCelula) {
        HashMap<String, Celula> celulas = this.tabuleiro.getCelulas();
        Celula cel = celulas.get(enderecoCelula);
        if (cel.getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
            this.setGanhou(false);
            this.setFimDoJogo(true);
            //revelar todas as células com bombas
        } else if (cel.getConteudoCelula().equals(ConteudoCelula.VAZIA) && !cel.isBandeira()) {
            ////revelar todas as células adjacentes recursivamente
            this.revelarCelulasAdjacentesACelulaVazia(cel);
            if(this.verificarSeGanhou()){
                this.setGanhou(true);
                this.setFimDoJogo(true);
            }
        } else if (!cel.isBandeira()) {
            cel.setRevelada(true);
            if(this.verificarSeGanhou()){
                this.setGanhou(true);
                this.setFimDoJogo(true);
            }
        }
    }

    public void colocarBandeira(String enderecoCelula) {
        HashMap<String, Celula> celulas = this.tabuleiro.getCelulas();
        Celula cel = celulas.get(enderecoCelula);
        cel.setBandeira(true);
        if (this.tabuleiro.getBombasRestantes() > 0) {
            this.tabuleiro.setBombasRestantes(this.tabuleiro.getBombasRestantes() - 1);
        }
    }

    public void retirarBandeira(String enderecoCelula) {
        HashMap<String, Celula> celulas = this.tabuleiro.getCelulas();
        Celula cel = celulas.get(enderecoCelula);
        cel.setBandeira(false);
        if (this.tabuleiro.getBombasRestantes() < this.tabuleiro.getNumBombas()) {
            this.tabuleiro.setBombasRestantes(this.tabuleiro.getBombasRestantes() + 1);
        }
    }

    public void revelarCelulasAdjacentesACelulaVazia(Celula celulaVazia) {
        HashMap<String, Celula> celulas = this.tabuleiro.getCelulas();
        celulaVazia.setRevelada(true);

        int numLinhas = this.tabuleiro.getDt().getNumLinhas();
        int numColunas = this.tabuleiro.getDt().getNumColunas();

        String linha = celulaVazia.getLinha();
        String coluna = celulaVazia.getColuna();

        StringBuilder sb = new StringBuilder(linha);
        sb.deleteCharAt(0);
        int l = Integer.parseInt(sb.toString());

        sb = new StringBuilder(coluna);
        sb.deleteCharAt(0);
        int c = Integer.parseInt(sb.toString());

        if ((c > 1 && c < numColunas) && (l > 1 && l < numLinhas)) {
            String emCima = "l" + (l - 1) + "c" + c;
            String embaixo = "l" + (l + 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
            String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);
            String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);
            String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorDireita));
                }
            } else if (!celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorEsquerda));
                }
            } else if (!celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorDireita));
                }
            } else if (!celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorEsquerda));
                }
            } else if (!celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                }
            }

        } else if (c == 1 && l == 1) {
            String embaixo = "l" + (l + 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorDireita));
                }
            } else if (!celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                }
            }

        } else if (c == numColunas && l == 1) {
            String embaixo = "l" + (l + 1) + "c" + c;
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorEsquerda));
                }
            } else if (!celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                }
            }

        } else if (c == 1 && l > 1 && l < numLinhas) {
            String emCima = "l" + (l - 1) + "c" + c;
            String embaixo = "l" + (l + 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
            String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorDireita));
                }
            } else if (!celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorDireita));
                }
            } else if (!celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                }
            }

        } else if (c == numColunas && l > 1 && l < numLinhas) {
            String emCima = "l" + (l - 1) + "c" + c;
            String embaixo = "l" + (l + 1) + "c" + c;
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);
            String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorEsquerda));
                }
            } else if (!celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorEsquerda));
                }
            } else if (!celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                }
            }

        } else if (l == numLinhas && c == 1) {
            String emCima = "l" + (l - 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorDireita));
                }
            } else if (!celulas.get(diagonalSuperiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorDireita).isRevelada()) {
                    celulas.get(diagonalSuperiorDireita).setRevelada(true);
                }
            }

        } else if (l == numLinhas && c == numColunas) {
            String emCima = "l" + (l - 1) + "c" + c;
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorEsquerda));
                }
            } else if (!celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                }
            }

        } else if (l == 1 && c > 1 && c < numColunas) {
            String embaixo = "l" + (l + 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalInferiorDireita = "l" + (l + 1) + "c" + (c + 1);
            String diagonalInferiorEsquerda = "l" + (l + 1) + "c" + (c - 1);

            if (celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(embaixo));
                }
            } else if (!celulas.get(embaixo).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(embaixo).isRevelada()) {
                    celulas.get(embaixo).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorDireita));
                }
            } else if (!celulas.get(diagonalInferiorDireita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorDireita).isRevelada()) {
                    celulas.get(diagonalInferiorDireita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalInferiorEsquerda));
                }
            } else if (!celulas.get(diagonalInferiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalInferiorEsquerda).isRevelada()) {
                    celulas.get(diagonalInferiorEsquerda).setRevelada(true);
                }
            }

        } else if (l == numLinhas && c > 1 && c < numColunas) {
            String emCima = "l" + (l - 1) + "c" + c;
            String direita = "l" + l + "c" + (c + 1);
            String esquerda = "l" + l + "c" + (c - 1);
            String diagonalSuperiorDireita = "l" + (l - 1) + "c" + (c + 1);
            String diagonalSuperiorEsquerda = "l" + (l - 1) + "c" + (c - 1);

            if (celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(emCima));
                }
            } else if (!celulas.get(emCima).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(emCima).isRevelada()) {
                    celulas.get(emCima).setRevelada(true);
                }
            }

            if (celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(esquerda));
                }
            } else if (!celulas.get(esquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(esquerda).isRevelada()) {
                    celulas.get(esquerda).setRevelada(true);
                }
            }

            if (celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(direita));
                }
            } else if (!celulas.get(direita).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(direita).isRevelada()) {
                    celulas.get(direita).setRevelada(true);
                }
            }

            if (celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.VAZIA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                    this.revelarCelulasAdjacentesACelulaVazia(celulas.get(diagonalSuperiorEsquerda));
                }
            } else if (!celulas.get(diagonalSuperiorEsquerda).getConteudoCelula().equals(ConteudoCelula.BOMBA)) {
                if (!celulas.get(diagonalSuperiorEsquerda).isRevelada()) {
                    celulas.get(diagonalSuperiorEsquerda).setRevelada(true);
                }
            }
        }

    }

}
