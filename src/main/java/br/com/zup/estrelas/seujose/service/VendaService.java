package br.com.zup.estrelas.seujose.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.entity.Peca;
import br.com.zup.estrelas.seujose.repository.VendaRepository;
import br.com.zup.estrelas.seujose.venda.dao.VendaDAO;

@Service
public class VendaService {

      private static final String QUANTIDADE_INSUFUCIENTE = "QUANTIDADE EM ESTOQUE INSUFICIENTE!";
      private static final String PECA_ADQUIRIDA_COM_SUCESSO = "PECA ADQUIRIDA COM SUCESSO!";
      private static final String PECA_NÃO_ENCONTRADA = "PEÇA NÃO ENCONTRADA PELO CODIGO DE BARRAS!";

      @Autowired
      VendaRepository repository;

      public Optional<Peca> pesquisaPecaASerComprada(String nome) {

            if (repository.existsByNome(nome)) {
                  return repository.findByNome(nome);
            }
            return null;
      }

      public MensagemDTO vendePeca(Long codBarras, VendaDAO venda) throws IOException {

            if (repository.existsById(codBarras)) {
                  Peca pecaComprada = repository.findById(codBarras).get();

                  if (pecaComprada.getQuantidadeEmEstoque() > venda.getQtdAComprar()) {

                        String nomeDaPeca = pecaComprada.getNome();

                        float valorTotalDaCompra = pecaComprada.getPrecoDeVenda() * venda.getQtdAComprar();

                        int qtdEstoque = pecaComprada.getQuantidadeEmEstoque();

                        venda.guardarVendaEmRelatório(codBarras, venda.getQtdAComprar(), nomeDaPeca,
                                    valorTotalDaCompra);

                        int qtdEstoqueAtualizada = qtdEstoque - venda.getQtdAComprar();
                        pecaComprada.setQuantidadeEmEstoque(qtdEstoqueAtualizada);

                        repository.save(pecaComprada);

                        return new MensagemDTO(
                                    PECA_ADQUIRIDA_COM_SUCESSO + " TOTAL DA COMPRA: R$ " + valorTotalDaCompra);
                  } else {
                        return new MensagemDTO(QUANTIDADE_INSUFUCIENTE);
                  }
            }
            return new MensagemDTO(PECA_NÃO_ENCONTRADA);
      }

}
