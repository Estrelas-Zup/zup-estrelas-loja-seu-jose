package br.com.zup.estrelas.seujose.venda.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VendaDAO {

      private static final String DIRETORIO = "C:\\ReplicaGit\\zup-estrelas-loja-seu-jose\\Vendas";

      private Integer qtdAComprar;

      public VendaDAO() {

      }

      public Integer getQtdAComprar() {
            return qtdAComprar;
      }

      public void setQtdAComprar(Integer qtdEstoque) {
            this.qtdAComprar = qtdEstoque;
      }

      public void guardarVendaEmRelatório(Long codBarras, Integer qtdAComprar, String nomeDaPeca,
                  float valorTotalDaCompra) throws IOException {

            File diretorioRelatoriosDeVenda = new File(DIRETORIO);
            if (!diretorioRelatoriosDeVenda.exists()) {
                  diretorioRelatoriosDeVenda.mkdir();
            }

            int qtdArquivosLojaSeuJose = diretorioRelatoriosDeVenda.listFiles().length;
            qtdArquivosLojaSeuJose++;

            FileWriter escrita = new FileWriter(DIRETORIO + "Vendas-Dia " + qtdArquivosLojaSeuJose + ".txt", true);
            BufferedWriter bw = new BufferedWriter(escrita);

            bw.append("Código: " + codBarras + "\t\t" + "Nome: " + nomeDaPeca + "\t\t" + "Quantidade: " + qtdAComprar
                        + "\t\t" + "Valor: " + valorTotalDaCompra + "\n");

            bw.close();
      }
}