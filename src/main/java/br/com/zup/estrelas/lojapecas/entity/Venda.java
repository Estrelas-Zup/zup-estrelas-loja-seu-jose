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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Venda {
	@Id
	@Column(name = "id_venda")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "codigo_de_barra", foreignKey=@ForeignKey(name="FK_CODIGO_DE_BARRAS"), nullable = false)
	private Peca peca;
	
	@Column(nullable = false, columnDefinition = "int default 0")
	private int quantidade;
	
	@Column(name = "preco_unitario", nullable = false)
	private double precoUnitario;
	
	@Column(name = "preco_total_venda", nullable = false)
	private double precoTotalVenda;
	
	@Column(name = "data_venda", nullable = false)
	private LocalDate dataVenda;
}
