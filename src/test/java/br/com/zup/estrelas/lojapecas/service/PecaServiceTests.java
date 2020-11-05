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

	private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
	private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
	private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
	private static final String PECA_INEXISTENTE = "Peça inexistente.";
	private static final String PECA_ALTERADA_COM_SUCESSO = "Peça alterada com sucesso.";

	private static void dadosDaPeca(Peca peca) {
		
		peca.setCodBarras(111L);
		peca.setNome("Farol");
		peca.setModelo("Strada");
		peca.setFabricante("Fiat");
		peca.setPrecoCusto((double) 100);
		peca.setPrecoVenda((double) 179);
		peca.setQtdEstoque(15);
		peca.setCategoria(Categoria.FUNILARIA);
	}
	
	@Mock
	PecaRepository pecaRepository;

	@InjectMocks
	PecaService pecaService;

	@Test
	public void deveAdicionarUmaPeca() {

		Peca peca = new Peca();
		dadosDaPeca(peca);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);

		Assert.assertEquals("Deve retornar uma mensagem de sucesso ao adicionar peça", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveAdicionarUmaPecaJaExistente() {

		Peca peca = new Peca();
		dadosDaPeca(peca);

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_JA_CADASTRADA);

		Assert.assertEquals("Deve retornar uma mensagem de falha ao adicionar peça", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void deveRemoverUmaPeca() {

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);

		Assert.assertEquals("Deve retornar uma mensagem de sucesso ao remover uma peça", mensagemEsperada,
				mensagemRetornada);
	}

	@Test
	public void naoDeveRemoverUmaPecaInexistente() {

		Mockito.when(pecaRepository.existsById(111L)).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.removePeca(111L);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);

		Assert.assertEquals("Deve retornar uma mensagem de falha ao remover a peça", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void deveAlterarUmaPeca() {

		Peca peca = new Peca();
		dadosDaPeca(peca);
		
		AlteraPecaDTO novaPeca = new AlteraPecaDTO();

		Optional<Peca> pecaConsultada = Optional.of(peca);
		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaConsultada);

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, novaPeca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
		
		Assert.assertEquals("Deve retornar mensagem de sucesso ao alterar uma peça", mensagemEsperada, mensagemRetornada);
	}
	
	@Test 
	public void naoDeveAlterarUmaPecaInexistente() {

		Peca peca = new Peca();
		dadosDaPeca(peca);
		
		AlteraPecaDTO novaPeca = new AlteraPecaDTO();

		Optional<Peca> pecaConsultada = Optional.empty();
		Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaConsultada);

		MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, novaPeca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);
		
		Assert.assertEquals("Deve retornar mensagem de erro ao alterar uma peça", mensagemEsperada, mensagemRetornada);
	}
}
