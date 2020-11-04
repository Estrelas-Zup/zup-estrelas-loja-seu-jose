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

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
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

	@DeleteMapping(path = "/{codigoBarra}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO removePeca(@PathVariable Long codigoBarra) {
		return pecaService.removePeca(codigoBarra);
	}

	@PutMapping(path = "/{codigoBarra}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public MensagemDTO alteraPeca(@PathVariable Long codigoBarra, @RequestBody Peca peca) {
		return pecaService.alteraPeca(codigoBarra, peca);
	}

	@GetMapping(path = "/{codigoBarra}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Peca buscaPeca(@PathVariable Long codigoBarra) {
		return pecaService.buscaPeca(codigoBarra);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> listaPeca() {
		return pecaService.listarPecas();
	}

	@GetMapping(path = "/nome/{nome}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorNome(@PathVariable String nome) {
		return pecaService.buscarPecaPorNome(nome);
	}

	@GetMapping(path = "/modelo/{modelo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorModelo(@PathVariable String modelo) {
		return pecaService.buscarPecaPorModelo(modelo);
	}

	@GetMapping(path = "/categoria/{categoria}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Peca> buscaPecaPorCategoria(@PathVariable String categoria) {
		return pecaService.buscarPecaPorCategoria(categoria);
	}
}
