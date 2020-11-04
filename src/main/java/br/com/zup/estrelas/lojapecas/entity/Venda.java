package br.com.zup.estrelas.lojapecas.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "venda")
public class Venda {
	
	@Id
	@Column(name = "ref_venda")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long referencia;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@ManyToOne
	@JoinColumn(name = "cod_barras", foreignKey = @ForeignKey(name = "FK_PECAVENDA"))
	private Peca peca;
	
	@Column(nullable = false)
	private Double valor;
	
	@Column(name = "data_venda", nullable = false)
	private LocalDate dataVenda;
	
	
	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Peca getPeca() {
		return peca;
	}

	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

}
