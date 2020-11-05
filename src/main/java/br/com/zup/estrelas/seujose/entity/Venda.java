package br.com.zup.estrelas.seujose.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Venda {

      @Id
      @Column(name = "id_venda")
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long Id;

      @ManyToOne
      @JoinColumn(name = "codigo_de_barras", foreignKey = @ForeignKey(name = "FK_VENDAS"))
      private Peca peca;

      @Column(name = "nome_da_peca", nullable = false)
      private String nome;

      @Column(name = "quantidade", nullable = false)
      private Integer qtdAComprar;

      @Column(name = "data_da_venda", nullable = false)
      private LocalDate dataDaVenda;

      @Column(name = "valor_da_compra", nullable = false)
      private Double valor;

      public Long getId() {
            return Id;
      }

      public void setId(Long id) {
            Id = id;
      }

      public Peca getPeca() {
            return peca;
      }

      public void setPeca(Peca peca) {
            this.peca = peca;
      }

      public String getNome() {
            return nome;
      }

      public void setNome(String nome) {
            this.nome = nome;
      }

      public Integer getQtdAComprar() {
            return qtdAComprar;
      }

      public void setQtdAComprar(Integer qtdAComprar) {
            this.qtdAComprar = qtdAComprar;
      }

      public LocalDate getDataVenda() {
            return dataDaVenda;
      }

      public void setDataVenda(LocalDate dataVenda) {
            this.dataDaVenda = dataVenda;
      }

      public Double getValor() {
            return valor;
      }

      public void setValor(Double valor) {
            this.valor = valor;
      }

}
