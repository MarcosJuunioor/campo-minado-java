/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author marco
 */
public class Celula {
    private String linha;
    private String coluna;
    private ConteudoCelula conteudoCelula;
    private boolean revelada;
    private boolean bandeira;
    
    public Celula(String linha, String coluna, ConteudoCelula conteudoCelula){
        this.linha = linha;
        this.coluna = coluna;
        this.conteudoCelula = conteudoCelula;
        this.revelada = false;
        this.bandeira = false;
    }

    /**
     * @return the linha
     */
    public String getLinha() {
        return linha;
    }

    /**
     * @param linha the linha to set
     */
    public void setLinha(String linha) {
        this.linha = linha;
    }

    /**
     * @return the coluna
     */
    public String getColuna() {
        return coluna;
    }

    /**
     * @param coluna the coluna to set
     */
    public void setColuna(String coluna) {
        this.coluna = coluna;
    }

    /**
     * @return the conteudoCelula
     */
    public ConteudoCelula getConteudoCelula() {
        return conteudoCelula;
    }

    /**
     * @param conteudoCelula the conteudoCelula to set
     */
    public void setConteudoCelula(ConteudoCelula conteudoCelula) {
        this.conteudoCelula = conteudoCelula;
    }

    /**
     * @return the revelada
     */
    public boolean isRevelada() {
        return revelada;
    }

    /**
     * @param revelada the revelada to set
     */
    public void setRevelada(boolean revelada) {
        this.revelada = revelada;
    }

    /**
     * @return the bandeira
     */
    public boolean isBandeira() {
        return bandeira;
    }

    /**
     * @param bandeira the bandeira to set
     */
    public void setBandeira(boolean bandeira) {
        this.bandeira = bandeira;
    }

    
}
