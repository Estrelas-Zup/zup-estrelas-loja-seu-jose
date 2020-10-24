package br.com.zup.estrelas.seujose.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import br.com.zup.estrelas.seujose.entity.categoria.Categoria;

@Entity
public class Peca {

      @Id
      @Column(name = "codigo_de_barras")
      private Long codigoDeBarras;

      @Column(nullable = false)
      private String nome;

      @Column(name = "modelo_do_carro", nullable = false)
      private String modeloDoCarro;

      @Column(nullable = false)
      private String fabricante;

      @Column(name = "preco_custo", nullable = false)
      private Float precoDeCusto;

      @Column(name = "preco_venda", nullable = false)
      private Float precoDeVenda;

      @Column(name = "qtd_estoque", nullable = false)
      private int qtdEmEstoque;

      @Column
      @Enumerated(EnumType.STRING)
      private Categoria categoria;

      public Long getCodigoDeBarras() {
            return codigoDeBarras;
      }

      public void setCodigoDeBarras(Long codigoDeBarras) {
            this.codigoDeBarras = codigoDeBarras;
      }

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
            return qtdEmEstoque;
      }

      public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
            this.qtdEmEstoque = quantidadeEmEstoque;
      }

      public Categoria getCategoria() {
            return categoria;
      }

      public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
      }

}
