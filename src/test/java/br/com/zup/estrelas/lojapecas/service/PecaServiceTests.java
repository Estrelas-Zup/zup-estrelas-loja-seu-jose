package br.com.zup.estrelas.lojapecas.service;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;

@RunWith(MockitoJUnitRunner.class)
public class PecaServiceTests {
	
    private static final String PECA_ALTERADA_COM_SUCESSO = "Peça alterada com sucesso.";
    private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
    private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
    private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
    private static final String PECA_INEXISTENTE = "Peça inexistente.";
    
	@Mock
	PecaRepository pecaRepository;

	@InjectMocks
	PecaService pecaService;

	@Test
	public void deveCadastrarUmaPeca() {
		Peca peca = montarObjetoPeca();

		Mockito.when(pecaRepository.existsById(peca.getCodBarras())).thenReturn(false);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);

		Assert.assertEquals("Mensagem deve ser indicativa de sucesso", mensagemEsperada, mensagemRetornada);
	}

	@Test
	public void naoDeveCadastrarPecaPoisElaExiste() {
		Peca peca = montarObjetoPeca();
		
		Mockito.when(pecaRepository.existsById(1L)).thenReturn(true);

		MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_JA_CADASTRADA);
		
		Assert.assertEquals("Mensagem deve ser indicativa de peca existente", mensagemEsperada, mensagemRetornada);
		
	}
	
	@Test
	public void deveAlterarPecaComSucesso() {
		AlteraPecaDTO alteraPecaDTO = new AlteraPecaDTO();
		
		Peca peca = montarObjetoPeca();
		Optional<Peca> pecaOptional = Optional.empty();	
		BeanUtils.copyProperties(pecaOptional, peca);
		
		
			
		alteraPecaDTO.setFabricante("VW");
		alteraPecaDTO.setModelo("Gol");
		alteraPecaDTO.setNome("Farol de milha");
		alteraPecaDTO.setQtdEstoque(10);
		alteraPecaDTO.setPrecoCusto(250.0);
		alteraPecaDTO.setPrecoVenda(480.0);
		alteraPecaDTO.setCategoria(Categoria.ACESSORIOS);
		
		Mockito.when(pecaRepository.findById(peca.getCodBarras())).thenReturn(pecaOptional);
		
		Mockito.when(pecaService.alteraPeca(codBarra, alteraPecaDTO))
		.thenReturn(new MensagemDTO(PECA_ALTERADA_COM_SUCESSO));
		
		MensagemDTO mensagemRetornada = pecaService.alteraPeca(codBarra, alteraPecaDTO);
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
		
		Assert.assertEquals("A mensagem retornada deve ser indicativo de peca alterada com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void deveExcluirPecaComSucesso() {
		Peca peca = montarObjetoPeca();
		
		Mockito.when(pecaRepository.existsById(peca.getCodBarras())).thenReturn(true);
		
		MensagemDTO mensagemRetornada = pecaService.removePeca(peca.getCodBarras());
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);
		
	    Assert.assertEquals("A mensagem retornada deve ser indicativo de peca excluida com sucesso", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void naoDeveExcluirPecaCasoNaoExiste() {
	Peca peca = montarObjetoPeca();
		
		Mockito.when(pecaRepository.existsById(peca.getCodBarras())).thenReturn(false);
		
		MensagemDTO mensagemRetornada = pecaService.removePeca(peca.getCodBarras());
		MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);
		
		 Assert.assertEquals("A mensagem deve indicar peça inexistente", mensagemEsperada, mensagemRetornada);
	}
	
	private Peca montarObjetoPeca() {
		Peca peca = new Peca();
		
		peca.setCodBarras(1L);
		peca.setFabricante("VW");
		peca.setModelo("Gol");
		peca.setNome("Farol de milha");
		peca.setQtdEstoque(10);
		peca.setPrecoCusto(250.0);
		peca.setPrecoVenda(480.0);
		peca.setCategoria(Categoria.ACESSORIOS);
		
		return peca;
	}
}
