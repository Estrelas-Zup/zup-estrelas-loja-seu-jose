package br.com.zup.estrelas.lojapecas.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;
import br.com.zup.estrelas.lojapecas.repository.VendaRepository;

import java.util.Optional;

import org.junit.Assert;

public class PecaServiceTests {
	private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
	private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
	private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
	private static final String PECA_INEXISTENTE = "Peça inexistente.";
	private static final String PECA_ALTERADA_COM_SUCESSO = "Peça alterada com sucesso.";

	@Mock
	PecaRepository pecaRepository;

	@InjectMocks
	PecaService pecaService;

	@Test
	public void deveAdicionarUmPeca() {

		Peca peca = new Peca();

		peca.setCodBarras(111L);
		peca.setFabricante("VW");
		peca.setModelo("Polo");
		peca.setNome("Volante");
		peca.setQtdEstoque(10);
		peca.setPrecoCusto(100.5);
		peca.setPrecoVenda(500.3);
		peca.setCategoria(Categoria.ACESSORIOS);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);

		Assert.assertEquals("Peça deve ser cadastrada com sucesso", mensagemEsperada, mensagemRetornada);

	}

	@Test
	public void naoDeveAdicionarUmPeca() {

		Peca peca = new Peca();

		peca.setCodBarras(111L);
		peca.setFabricante("VW");
		peca.setModelo("Polo");
		peca.setNome("Volante");
		peca.setQtdEstoque(10);
		peca.setPrecoCusto(100.5);
		peca.setPrecoVenda(500.3);
		peca.setCategoria(Categoria.ACESSORIOS);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_JA_CADASTRADA);

		Assert.assertEquals("Não deve cadastrar uma peça que já existe", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void deveRemoverUmaPeca() {
		Peca peca = new Peca();

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);

		Assert.assertEquals("Peça deve ser removida com sucesso", mensagemEsperada, mensagemRetornada);

	}

	@Test
	public void naoDeveRemoverUmaPeca() {
		Peca peca = new Peca();

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals("Não deve remover uma  peça inexistente", mensagemEsperada, mensagemRetornada);

	}

	// Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaAlterada);
	@Test
	public void deveAlterarUmaPeca() {

		Peca peca = new Peca();

		Optional<Peca> pecaArmazenada = Optional.of(peca);

		//Mockito.when(pecaRepository.existsById(111L)).thenReturn(pecaArmazenada);

		AlteraPecaDTO alteraPeca = new AlteraPecaDTO();

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, alteraPeca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);

		Assert.assertEquals("Peça alterada com sucesso", mensagemEsperada, mensagemRetornada);

	}

	@Test
	public void naoDeveAlterarUmaPeca() {

		Peca peca = new Peca();

		Optional<Peca> pecarArmazenada = Optional.empty();

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		AlteraPecaDTO alteraPeca = new AlteraPecaDTO();

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, alteraPeca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals("Não d eve alterar uma peça inexistente", mensagemEsperada, mensagemRetornada);
	}

}
