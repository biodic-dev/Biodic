package com.example.dicbio.modelo;

public class Dicionario {

    private int idPalavra;
    private String Palavra;
    private String Origem;
    private String Descricao;
    private String Letra;

    public Dicionario() {
    }

    public int getIdPalavra() {
        return idPalavra;
    }

    public void setIdPalavra(int idPalavra) {
        this.idPalavra = idPalavra;
    }

    public String getPalavra() {
        return Palavra;
    }

    public void setPalavra(String palavra) {
        Palavra = palavra;
    }

    public String getOrigem() {
        return Origem;
    }

    public void setOrigem(String origem) {
        Origem = origem;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getLetra() {
        return Letra;
    }

    public void setLetra(String letra) {
        Letra = letra;
    }
    @Override
    public String toString(){
        return Palavra;
    }



}
