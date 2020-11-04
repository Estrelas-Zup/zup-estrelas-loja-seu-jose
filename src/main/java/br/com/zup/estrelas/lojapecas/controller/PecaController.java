package br.com.zup.estrelas.lojapecas.controller;

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

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.service.IPecaService;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    
    @Autowired
    IPecaService pecaService;

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO adicionaPeca(@RequestBody Peca peca) {
        return pecaService.adicionaPeca(peca);
    }

    @GetMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public Peca buscaPeca(@PathVariable Long codBarras) {
        return pecaService.buscaPeca(codBarras);
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public List<Peca> listaPecas() {
        return pecaService.listaPecas();
    }

    @DeleteMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO removePeca(@PathVariable Long codBarras) {
        return pecaService.removePeca(codBarras);
    }

    @PutMapping(path = "/{codBarras}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public MensagemDTO alteraPeca(@PathVariable Long codBarras, @RequestBody AlteraPecaDTO peca) {
        return pecaService.alteraPeca(codBarras, peca);
    }
    
    @GetMapping (path = "/nome/{nome}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorNome(@PathVariable String nome) {
		return pecaService.buscarPecaByNome(nome);
	}
	
	@GetMapping (path = "/modelo/{modelo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorModelo(@PathVariable String modelo) {
		return pecaService.buscarPecaByModelo(modelo);
	}
	
	@GetMapping (path = "/categoria/{categoria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorCategoria(@PathVariable Categoria categoria) {
		return pecaService.buscarPecaByCategoria(categoria);
	}

}
