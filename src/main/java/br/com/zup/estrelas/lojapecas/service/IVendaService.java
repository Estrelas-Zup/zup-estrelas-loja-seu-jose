package br.com.zup.estrelas.lojapecas.service;

import java.util.List;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaRelatorioDTO;
import br.com.zup.estrelas.lojapecas.entity.Venda;

public interface IVendaService {

	MensagemDTO realizaVenda(Venda venda);

	MensagemDTO geraRelatorio();

	List<VendaRelatorioDTO> listaVendas();
}
