package br.com.zup.estrelas.lojapecas.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
	private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
    private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
    private static final String PECA_INEXISTENTE = "Peça inexistente.";

	private static Peca criaUmaPeca() {
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
    
	@Mock
	PecaRepository pecaRepository;

    @InjectMocks
    PecaService pecaService;
    
    @Test
    public void deveCadastrarPecaComSucesso() {
    	
    	Peca peca = criaUmaPeca();
    	long codigoBarrasPeca = peca.getCodBarras();
        
        
        Mockito.when(pecaRepository.existsById(codigoBarrasPeca)).thenReturn(false);
        
        MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
        MensagemDTO mensagemEsperada = new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);
        
        Assert.assertEquals("Deve cadastrar normalmente", mensagemEsperada, mensagemRetornada);
    }
    
    @Test
    public void naoDeveCadastrarPecaExistente() {
    	
    	Peca peca = criaUmaPeca();
    	long codigoBarrasPeca = peca.getCodBarras();
        
        Mockito.when(pecaRepository.existsById(codigoBarrasPeca)).thenReturn(true);
        
        MensagemDTO mensagemRetornada = pecaService.adicionaPeca(peca);
        MensagemDTO mensagemEsperada = new MensagemDTO(PECA_JA_CADASTRADA);
        
        Assert.assertEquals("Não deve cadastrar a peca", mensagemEsperada, mensagemRetornada);
    }
    
    @Test
    public void atualizaPecaComSucesso() {
    	
    	Peca peca = criaUmaPeca();
        
        AlteraPecaDTO novaPeca = new AlteraPecaDTO();
        BeanUtils.copyProperties(peca, novaPeca);
        novaPeca.setModelo("Fusca");
        
        Optional<Peca> pecaAlterada = Optional.of(peca);
        Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaAlterada);
        
        
        MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, novaPeca);
        MensagemDTO mensagemEsperada = new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
        
        Assert.assertEquals("Deve atualizar a peca com sucesso", mensagemEsperada, mensagemRetornada);
    }
    
    @Test
    public void naoAtualizaPecaInexistente() {
    	
        AlteraPecaDTO novaPeca = new AlteraPecaDTO();
        
        Optional<Peca> pecaAlterada = Optional.empty();
        Mockito.when(pecaRepository.findById(111L)).thenReturn(pecaAlterada);
        
        
        MensagemDTO mensagemRetornada = pecaService.alteraPeca(111L, novaPeca);
        MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);
        
        Assert.assertEquals("Não existe peça para ser atualizada", mensagemEsperada, mensagemRetornada);
    }
    
    
    @Test
    public void removePecaComSucesso() {
    	
    	Peca peca = criaUmaPeca();
    	long codigoBarrasPeca = peca.getCodBarras();
        
        Mockito.when(pecaRepository.existsById(codigoBarrasPeca)).thenReturn(true);
        
        MensagemDTO mensagemRetornada = pecaService.removePeca(codigoBarrasPeca);
        MensagemDTO mensagemEsperada = new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);
        
        Assert.assertEquals("A peça deve ser removida", mensagemEsperada, mensagemRetornada);
    }
    
    @Test
    public void naoRemovePecaCasoElaNaoExista() {
    	
    	Peca peca = criaUmaPeca();
    	long codigoBarrasPeca = peca.getCodBarras();
        
        Mockito.when(pecaRepository.existsById(codigoBarrasPeca)).thenReturn(false);
        
        MensagemDTO mensagemRetornada = pecaService.removePeca(codigoBarrasPeca);
        MensagemDTO mensagemEsperada = new MensagemDTO(PECA_INEXISTENTE);
        
        Assert.assertEquals("Não existe peça para ser removida", mensagemEsperada, mensagemRetornada);
    }
}
