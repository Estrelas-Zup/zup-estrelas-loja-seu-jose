package br.com.zup.estrelas.lojapecas.dto;

public class VendaDTO {

    private long codBarras;

    private int quantidade;

    public long getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(long codBarras) {
        this.codBarras = codBarras;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
