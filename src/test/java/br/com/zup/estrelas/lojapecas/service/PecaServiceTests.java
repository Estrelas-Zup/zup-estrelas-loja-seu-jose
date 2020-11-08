package br.com.zup.estrelas.lojapecas.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;
import br.com.zup.estrelas.lojapecas.repository.VendaRepository;

@RunWith(MockitoJUnitRunner.class)
public class PecaServiceTests {

	private static final String CADASTRO_DA_PECA_REALIZADO_COM_SUCESSO = "PEÇA CADASTRADA COM SUCESSO!";
	private static final String PECA_EXISTENTE = "PEÇA JÁ EXISTENTE";
	private static final String PECA_REMOVIDA = "PEÇA REMOVIDA COM SUCESSO!";
	private static final String PECA_INEXISTENTE = "PEÇA INEXISTENTE NO NOSSO SISTEMA";
	private static final String PECA_ALTERADA_COM_SUCESSO = "PEÇA ALTERADA COM SUCESSO";
	private static final String MENSAGEM_DE_CADASTRO_DA_PECA = "ENVIA UMA MENSAGEM DE SUCESSO AO ATUALIZAR A PEÇA!";
	private static final String MENSAGEM_CASO_A_PECA_JÁ_EXISTA = "ENVIA UMA MENSAGEM QUE NÃO CADASTRA UMA PEÇA QUE JÁ EXISTE";
	private static final String MENSAGEM_CASO_A_PECA_FOR_REMOVIDA_COM_SUCESSO = "ENVIA UMA MENSAGEM CASO A PEÇA FOR REMOVIDA COM SUCESSO";
	private static final String MENSAGEM_CASO_A_PECA_NAO_EXISTA = "ENVIA UMA MENSAGEM CASO A PEÇA NÃO EXISTA";
	private static final String MENSAGEM_DE_SUCESSO_CASO_PECA_SEJA_ALTERADA = "ENVIA UMA MENSAGEM DE SUCESSO AO ALTERAR A PEÇA!";
	private static final String MENSAGEM_DE_ERRO_CASO_PECA_NAO_SEJA_ALTERADA = "ENVIA UMA MENSAGEM DE ERRO AO ALTERAR A PEÇA";

	@Mock
	PecaRepository pecaRepository;

	@InjectMocks
	PecaService pecaService;

	public void informacoesDaPeca(Peca peca) {
		peca.setCodBarras(111L);
		peca.setFabricante("VW");
		peca.setModelo("Polo");
		peca.setNome("Volante");
		peca.setQtdEstoque(10);
		peca.setPrecoCusto(100.5);
		peca.setPrecoVenda(500.3);
		peca.setCategoria(Categoria.ACESSORIOS);
	}

	@Test
	public void adicionaPecaComSucesso() {
		Peca peca = new Peca();
		informacoesDaPeca(peca);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_DA_PECA_REALIZADO_COM_SUCESSO);

		Assert.assertEquals(MENSAGEM_DE_CADASTRO_DA_PECA, mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoAdicionaPecaComSucesso() {
		Peca peca = new Peca();
		informacoesDaPeca(peca);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_EXISTENTE);

		Assert.assertEquals(MENSAGEM_CASO_A_PECA_JÁ_EXISTA, mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void pecaRemovidaComSucesso() {
		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA);

		Assert.assertEquals(MENSAGEM_CASO_A_PECA_FOR_REMOVIDA_COM_SUCESSO, mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void pecaInexistenteSemRemocao() {
		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals(MENSAGEM_CASO_A_PECA_NAO_EXISTA, mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void sePossivelAlteraUmaPeca() {

		Peca peca = new Peca();
		informacoesDaPeca(peca);

		AlteraPecaDTO pecaAlterada = new AlteraPecaDTO();

		Optional<Peca> pecaProcurada = Optional.of(peca);
		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaProcurada);

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, pecaAlterada);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);

		Assert.assertEquals(MENSAGEM_DE_SUCESSO_CASO_PECA_SEJA_ALTERADA, mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoAlteraPecaSeCasoNaoExista() {

		Peca peca = new Peca();
		informacoesDaPeca(peca);

		AlteraPecaDTO pecaAlterada = new AlteraPecaDTO();

		Optional<Peca> pecaProcurada = Optional.empty();
		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaProcurada);

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, pecaAlterada);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals(MENSAGEM_DE_ERRO_CASO_PECA_NAO_SEJA_ALTERADA, mensagemRetornada, mensagemEsperada);

	}

}
