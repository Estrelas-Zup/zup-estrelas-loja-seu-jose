package br.com.zup.estrelas.seujose.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.entity.Peca;
import br.com.zup.estrelas.seujose.service.VendaService;
import br.com.zup.estrelas.seujose.venda.dao.VendaDAO;

@RestController
@RequestMapping("/compra")
public class VendaController {

      @Autowired
      VendaService vendaService;

      @GetMapping(path = "/{nome}", produces = MediaType.APPLICATION_JSON_VALUE)
      public Optional<Peca> pesquisaPecaASerComprada(@PathVariable String nome) {
            return vendaService.pesquisaPecaASerComprada(nome);
      }

      @PutMapping(path = "/{codBarras}")
      public MensagemDTO vendePeca(@PathVariable Long codBarras, @RequestBody VendaDAO venda) throws IOException {
            return vendaService.vendePeca(codBarras, venda);
      }
}