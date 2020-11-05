package br.com.zup.estrelas.seujose.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.seujose.dto.AlteraPecaDTO;
import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.entity.Peca;
import br.com.zup.estrelas.seujose.service.PecaService;

@RestController
@RequestMapping("/pecas")
public class PecaController {

      @Autowired
      private PecaService pecaService;

      @PostMapping
      public MensagemDTO inserePecas(@RequestBody Peca peca) {
            return pecaService.adicionaPeca(peca);
      }

      @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
      public List<Peca> listaPecas() {
            return pecaService.listaPecas();
      }

      @GetMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
      public Peca listaPecaPorCodBarras(@PathVariable Long codBarras) {
            return pecaService.listaPecaPorCodBarras(codBarras);
      }

      @DeleteMapping(path = "/{codBarras}")
      public MensagemDTO removerPecaPorCodBarras(@PathVariable Long codBarras) {
            return pecaService.removePecaPorCodBarras(codBarras);
      }

      @PutMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
      public MensagemDTO alteraPeca(@PathVariable Long codBarras, @RequestBody AlteraPecaDTO pecaDTO) {
            return pecaService.alteraPeca(codBarras, pecaDTO);
      }

}