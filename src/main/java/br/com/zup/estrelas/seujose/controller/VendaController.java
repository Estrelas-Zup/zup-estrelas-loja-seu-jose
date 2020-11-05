package br.com.zup.estrelas.seujose.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.dto.VendaDTO;
import br.com.zup.estrelas.seujose.dto.VendaRelatorioDTO;
import br.com.zup.estrelas.seujose.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {

      @Autowired
      VendaService vendaService;

      @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
      public List<VendaRelatorioDTO> pesquisaPecaASerComprada() {
            return vendaService.listaVendas();
      }

      @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
      public MensagemDTO realizaVenda(@RequestBody VendaDTO venda) throws IOException {
            return vendaService.realizaVenda(venda);
      }

      @PostMapping(path = "/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
      public MensagemDTO geraRelatorioDeVenda() {
            return vendaService.geraRelatorioDeVenda();
      }
}