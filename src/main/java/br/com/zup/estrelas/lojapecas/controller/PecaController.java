package br.com.zup.estrelas.lojapecas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.service.IPecaService;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    // POST
    // GET -: Busca
    // GET -: Listagem
    // DELETE
    // PUT
    @Autowired
    IPecaService pecaService;

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO adicionaPeca(@RequestBody Peca peca) {
        return pecaService.adicionaPeca(peca);
    }

    // host:8080/pecas/12345 -> variável
    @GetMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Peca buscaPeca(@PathVariable Long codBarras) {
        return pecaService.buscaPeca(codBarras);
    }

    // host:8080/pecas
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Peca> listaPecas() {
        return pecaService.listaPecas();
    }

    // host:8080/pecas/12345 -> DELETE
    @DeleteMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO removePeca(@PathVariable Long codBarras) {
        return pecaService.removePeca(codBarras);
    }

    // host:8080/pecas/12345 -> PUT
    @PutMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO alteraPeca(@PathVariable Long codBarras, @RequestBody AlteraPecaDTO peca) {
        return pecaService.alteraPeca(codBarras, peca);
    }

}
