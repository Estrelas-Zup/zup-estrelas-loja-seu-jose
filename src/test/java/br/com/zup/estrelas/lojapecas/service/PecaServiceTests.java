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
	
	@Test
    public void deveAdicionarUmaPeca() {
		
		Peca peca = criaPreca ();
		
		Mockito.when(pecaRepository.existsById(222L)).thenReturn(false);
		
		MensagemDTO mensagemRetornada = this.pecaService.adicionaPeca(peca);
        MensagemDTO mensagemEsperada = new MensagemDTO("Cadastro realizado com sucesso.");
        
        Assert.assertEquals("A mensagem deve retornar sucesso na inclusão da peça", mensagemEsperada, mensagemRetornada);
	}
	
	@Test
	public void NaoDeveAdicionarUmaPeca() {

		Peca peca = criaPreca ();
		
		Mockito.when(pecaRepository.existsById(222L)).thenReturn(true);
		
		MensagemDTO mensagemRetornada = this.pecaService.adicionaPeca(peca);
		MensagemDTO mensagemEsperada = new MensagemDTO("O cadastro não ocorreu, peça já está cadastrada");
		
		Assert.assertEquals("Não pode cadastrar peça que já existe", mensagemEsperada, mensagemRetornada);

	}
	
	@Test
	public void deveAlterarUmaPeca() {

		Peca peca = criaPreca ();
		
		Optional <Peca>  pecaOptional = Optional.of(peca);
		
		Mockito.when(pecaRepository.findById(222L)).thenReturn(pecaOptional);
		
		AlteraPecaDTO pecaDto = new AlteraPecaDTO();

		pecaDto.setNome("Capa");
		pecaDto.setModelo("Agile");
		pecaDto.setFabricante("Chevrolet");
		pecaDto.setPrecoCusto(100.0);
		pecaDto.setPrecoVenda(200.0);
		pecaDto.setQtdEstoque(150);
		pecaDto.setCategoria(Categoria.ACESSORIOS);
		
		MensagemDTO mensagemRetornada = this.pecaService.alteraPeca(peca.getCodBarras(), pecaDto);
        MensagemDTO mensagemEsperada = new MensagemDTO("Peça alterada com sucesso.");
        
        Assert.assertEquals("A mensagem deve retornar sucesso na alteracao da peça", mensagemEsperada, mensagemRetornada);

	}
	
	@Test
	public void naoDeveAlterarPecaInexistente () {
		
		AlteraPecaDTO pecaDto = new AlteraPecaDTO();
		
		Mockito.when(pecaRepository.findById(333L)).thenReturn(Optional.empty());
		
		MensagemDTO mesnsagemRetornada = this.pecaService.alteraPeca(333L, pecaDto);
		MensagemDTO mensagemEsperada = new MensagemDTO ("Peça inexistente.");
		
		Assert.assertEquals("Deve retornar qua a peça não foi alterada", mensagemEsperada, mesnsagemRetornada);
	}

	
	@Test
    public void deveRemoverUmaPeca() {
		
		Mockito.when(pecaRepository.existsById(222L)).thenReturn(true);
		
		MensagemDTO mensagemRetornada = this.pecaService.removePeca(222L);
		MensagemDTO mensagemEsperada = new MensagemDTO("Peça removida com sucesso!");
		
		Assert.assertEquals("A peca será removida", mensagemEsperada, mensagemRetornada);
		
	}
	
	@Test
	public void NaoDeveRemoverUmaPeca() {

		Mockito.when(pecaRepository.existsById(222L)).thenReturn(false);

		MensagemDTO mensagemRetornada = this.pecaService.removePeca(222L);
		MensagemDTO mensagemEsperada = new MensagemDTO("Peça inexistente.");

		Assert.assertEquals("A peca não será removida porque não existe", mensagemEsperada, mensagemRetornada);
	}
	
	static Peca criaPreca () {
		
		Peca peca = new Peca();

		peca.setCodBarras(222L);
		peca.setNome("Lanterna");
		peca.setModelo("Agile");
		peca.setFabricante("Chevrolet");
		peca.setPrecoCusto(100.0);
		peca.setPrecoVenda(200.0);
		peca.setQtdEstoque(100);
		peca.setCategoria(Categoria.ACESSORIOS);
		
		return peca;
	}

}
