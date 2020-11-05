package br.com.zup.estrelas.seujose.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.dto.AlteraPecaDTO;
import br.com.zup.estrelas.seujose.entity.Peca;
import br.com.zup.estrelas.seujose.repository.PecaRepository;

@Service
public class PecaService {

      private static final String PECA_JA_EXISTENTE = "JÁ EXISTE NO BANCO DE DADOS UMA PEÇA COM ESSE CÓDIGO DE BARRAS!";
      private static final String PECA_ADICIONADA_COM_SUCESSO = "A PEÇA FOI ADICIONADA COM SUCESSO NO BANCO DE DADOS!";
      private static final String PECA_REMOVIDA_COM_SUCESSO = "PEÇA REMOVIDA COM SUCESSO!";
      private static final String PECA_NÃO_ENCONTRADA = "PEÇA NÃO ENCONTRADA PELO CODIGO DE BARRAS!";
      private static final String PECA_ALTERADA_COM_SUCESSO = "PEÇA ALTERADA COM SUCESSO!";
      private static final String PECA_NÃO_ALTERADA = "PEÇA NÃO ENCONTRADA PARA FAZER A ALTERAÇÂO!";
      

      @Autowired
      PecaRepository repository;

      public MensagemDTO adicionaPeca(Peca peca) {

            if (repository.existsById(peca.getCodBarras())) {
                  return new MensagemDTO(PECA_JA_EXISTENTE);
            }

            repository.save(peca);
            return new MensagemDTO(PECA_ADICIONADA_COM_SUCESSO);
      }

      public List<Peca> listaPecas() {
            return (List<Peca>) repository.findAll();
      }

      public Peca listaPecaPorCodBarras(Long codBarras) {
            if (repository.existsById(codBarras)) {
                  return repository.findById(codBarras).get();
            }
            return null;
      }

      public MensagemDTO removePecaPorCodBarras(Long codBarras) {

            if (!repository.existsById(codBarras)) {
                  return new MensagemDTO(PECA_NÃO_ENCONTRADA);
            }

            repository.deleteById(codBarras);
            return new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);
      }

      public MensagemDTO alteraPeca(Long codBarras, AlteraPecaDTO pecaDTO) {

            Peca pecaPojo = new Peca();

            Optional<Peca> pecaConsultada = repository.findById(codBarras);

            if (pecaConsultada.isPresent()) {

                  pecaPojo = pecaConsultada.get();

                  pecaPojo.setNome(pecaDTO.getNome());
                  pecaPojo.setModeloDoCarro(pecaDTO.getModeloDoCarro());
                  pecaPojo.setFabricante(pecaDTO.getFabricante());
                  pecaPojo.setPrecoDeCusto(pecaDTO.getPrecoDeCusto());
                  pecaPojo.setPrecoDeVenda(pecaDTO.getPrecoDeVenda());
                  pecaPojo.setQuantidadeEmEstoque(pecaDTO.getQuantidadeEmEstoque());

                  repository.save(pecaPojo);
                  return new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
            }
            return new MensagemDTO(PECA_NÃO_ALTERADA);
      }
}