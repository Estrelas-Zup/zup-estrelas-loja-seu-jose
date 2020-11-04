package br.com.zup.estrelas.lojapecas.service;

import java.time.LocalDate;
import java.util.List;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.entity.Venda;

public interface IVendaService {
	public MensagemDTO realizaVenda(VendaDTO vendaDTO);
	
	public List<Venda> buscaVendasRealizadas();
	
	public List<Venda> buscaVendasPorData(LocalDate dataVenda);

	public List<Venda> buscaVendasPorAno(LocalDate dataAnoInicio, LocalDate dataAnoFim);
	
	public MensagemDTO gerarRelatorio();
}
