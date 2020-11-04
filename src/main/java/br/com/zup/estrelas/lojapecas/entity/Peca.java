package br.com.zup.estrelas.lojapecas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Peca {
	@Id
	@Column(name = "codigo_de_barra")
	private Long codigoBarra;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String modelo;
	
	@Column(nullable = false)
	private String fabricante;
	
	@Column(name = "preco_de_custo", nullable = false)
	private Double precoCusto;
	
	@Column(name = "preco_de_venda", nullable = false)
	private Double precoVenda;
	
	@Column(name = "quantidade_estoque", columnDefinition = "int default 0")
	private int quantidadeEstoque;
	
	@Column(nullable = false)
	private String categoria;
}
