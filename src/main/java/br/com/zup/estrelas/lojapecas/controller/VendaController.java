package br.com.zup.estrelas.lojapecas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaRelatorioDTO;
import br.com.zup.estrelas.lojapecas.service.IVendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    IVendaService vendaService;

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO realizaVenda(@RequestBody VendaDTO venda) {
        return vendaService.realizaVenda(venda);
    }

    @PostMapping(path = "/relatorio", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO geraRelatorio() {
        return vendaService.geraRelatorio();
    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<VendaRelatorioDTO> listaVendas()  {
        return vendaService.listaVendas();
    }

}
