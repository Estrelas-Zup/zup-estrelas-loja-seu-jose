package br.com.zup.estrelas.lojapecas.service;

import java.util.List;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaRelatorioDTO;

public interface IVendaService {

    MensagemDTO realizaVenda(VendaDTO venda);

    MensagemDTO geraRelatorio();

    List<VendaRelatorioDTO> listaVendas();
}
