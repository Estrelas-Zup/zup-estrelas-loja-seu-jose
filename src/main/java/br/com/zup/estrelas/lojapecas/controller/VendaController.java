package br.com.zup.estrelas.lojapecas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.entity.Venda;
import br.com.zup.estrelas.lojapecas.service.IVendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	@Autowired
	IVendaService vendaService;
	
	@PostMapping (produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO realizarVenda (@RequestBody VendaDTO vendaDTO) {
		return vendaService.realizaVenda(vendaDTO);
	}
	
	@PostMapping (path = "/relatorio", produces = { MediaType.APPLICATION_JSON_VALUE})
	public MensagemDTO gerarRelatorio() {
		return vendaService.gerarRelatorio();
	}
	
	@GetMapping (produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Venda> buscaVendasRealizadas(){
		return vendaService.buscaVendasRealizadas();
	}
	
	@GetMapping (path = "/{dia}/{mes}/{ano}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Venda> buscaVendasPorData(@PathVariable Integer dia, @PathVariable Integer mes, @PathVariable Integer ano){
		return vendaService.buscaVendasPorData(LocalDate.of(ano, mes, dia));
	}
	
	@GetMapping (path = "/{ano}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Venda> buscarVendasPorAno(@PathVariable Integer ano){
		return vendaService.buscaVendasPorAno(LocalDate.of(ano, 01, 01), LocalDate.of(ano, 12, 31));
	}
}
