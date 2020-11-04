package br.com.zup.estrelas.lojapecas.dto;

public class VendaRelatorioDTO {

	private long referencia;

    private int quantidade;

    private String nome;

    private Double valor;

    public long getReferencia() {
        return referencia;
    }

    public void setReferencia(long codBarras) {
        this.referencia = codBarras;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
