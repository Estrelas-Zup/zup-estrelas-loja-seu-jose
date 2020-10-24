package br.com.zup.estrelas.seujose.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.zup.estrelas.seujose.entity.categoria.Categoria;

public class alteraPecaDTO {

	private String nome;

	private String modeloDoCarro;

	private String fabricante;

	private Float precoDeCusto;

	private Float precoDeVenda;

	private int quantidadeEmEstoque;

	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getModeloDoCarro() {
		return modeloDoCarro;
	}

	public void setModeloDoCarro(String modeloDoCarro) {
		this.modeloDoCarro = modeloDoCarro;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Float getPrecoDeCusto() {
		return precoDeCusto;
	}

	public void setPrecoDeCusto(Float precoDeCusto) {
		this.precoDeCusto = precoDeCusto;
	}

	public Float getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(Float precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}

	public int getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
