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
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;

@RunWith(MockitoJUnitRunner.class)
public class PecaServiceTests {

	@Mock
	PecaRepository pecaRepository;

	@InjectMocks
	PecaService pecaService;

	private static final String PECA_ALTERADA_COM_SUCESSO = "Peça alterada com sucesso.";
	private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
	private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
	private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
	private static final String PECA_INEXISTENTE = "Peça inexistente.";

	@Test
	public void deveAdicionarPecaComSucesso() {
		Peca peca = instanciaPeca();

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = this.pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);

		Assert.assertEquals("Deve adicionar peça com sucesso", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveAdionarPecaExistente() {
		Peca peca = instanciaPeca();

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = this.pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_JA_CADASTRADA);

		Assert.assertEquals("Não deve adicionar uma peça já existente", mensagemEsperada, mensagemRetornada);

	}

	@Test
	public void deveAlterarPecaComSucesso() {
		Peca peca = instanciaPeca();

		Optional<Peca> pecaBD = Optional.of(peca);

		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaBD);

		AlteraPecaDTO alteraPecaDTO = instanciaAlteraPecaDTO();

		MensagemDTO mensagemRetornada = this.pecaService.alteraPeca(111L, alteraPecaDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);

		Assert.assertEquals("Deve alterar peça ja cadastrada com sucesso", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveAlterarPecaInexistente() {

		Optional<Peca> pecaBD = Optional.empty();

		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaBD);

		AlteraPecaDTO alteraPecaDTO = instanciaAlteraPecaDTO();

		MensagemDTO mensagemRetornada = this.pecaService.alteraPeca(111L, alteraPecaDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals("Não deve alterar uma peça inexistente", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void deveRemovePecaComSucesso() {

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = this.pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);

		Assert.assertEquals("Deve remover peça existente com sucesso", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveRemoverPecaInexistente() {

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = this.pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals("Não deve remover uma peça inexistente", mensagemEsperada, mensagemRetornada);
	}

	private Peca instanciaPeca() {
		Peca peca = new Peca();
		peca.setCodBarras(111L);
		peca.setFabricante("VW");
		peca.setModelo("Polo");
		peca.setNome("Volante");
		peca.setQtdEstoque(10);
		peca.setPrecoCusto(100.5);
		peca.setPrecoVenda(500.3);
		peca.setCategoria(Categoria.ACESSORIOS);

		return peca;
	}

	private AlteraPecaDTO instanciaAlteraPecaDTO() {
		AlteraPecaDTO alteraPecaDTO = new AlteraPecaDTO();
		alteraPecaDTO.setFabricante("Fiat");
		alteraPecaDTO.setCategoria(Categoria.MANUTENCAO);
		alteraPecaDTO.setModelo("Argo");
		alteraPecaDTO.setNome("Freio");
		alteraPecaDTO.setPrecoCusto(298.90);
		alteraPecaDTO.setPrecoVenda(417.56);
		alteraPecaDTO.setQtdEstoque(50);

		return alteraPecaDTO;
	}
}
