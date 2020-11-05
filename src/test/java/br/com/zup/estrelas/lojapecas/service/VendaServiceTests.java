package br.com.zup.estrelas.lojapecas.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.VendaRepository;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceTests {

    @Mock
    PecaService pecaService;

    @Mock
    VendaRepository vendaRepository;

    @InjectMocks
    VendaService vendaService;

    private static void dadosDaVenda(Peca peca) {
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
    public void deveRealizarUmaVenda() {
        Peca peca = new Peca();
        dadosDaVenda(peca); 

        VendaDTO vendaDto = new VendaDTO();
        vendaDto.setCodBarras(111L);
        vendaDto.setQuantidade(10);

        Mockito.when(pecaService.buscaPeca(111L)).thenReturn(peca);
        Mockito.when(pecaService.alteraPeca(Mockito.any(), Mockito.any()))
                .thenReturn(new MensagemDTO("Peça alterada com sucesso."));

        MensagemDTO mensagemRetornada = this.vendaService.realizaVenda(vendaDto);
        MensagemDTO mensagemEsperada = new MensagemDTO("Venda realizada com sucesso.");

        Assert.assertEquals("A mensagem retornada deve ser indicativo de sucesso", mensagemEsperada, mensagemRetornada);
    }

    @Test
    public void naoDeveRealizarUmaVendaCasoAPecaNaoExista() {
        VendaDTO vendaDto = new VendaDTO();
        vendaDto.setCodBarras(111L);
        vendaDto.setQuantidade(10);

        Mockito.when(pecaService.buscaPeca(111L)).thenReturn(null);

        MensagemDTO mensagemRetornada = this.vendaService.realizaVenda(vendaDto);
        MensagemDTO mensagemEsperada = new MensagemDTO("Peça inexistente");

        Assert.assertEquals("A mensagem deve indicar peça inexistente", mensagemEsperada, mensagemRetornada);
    }

    @Test
    public void naoDeveRealizarUmaVendaCasoNaoHajaPecasSuficientes() {
        Peca peca = new Peca();
        dadosDaVenda(peca); 
        
        VendaDTO vendaDto = new VendaDTO();
        vendaDto.setCodBarras(111L);
        vendaDto.setQuantidade(15);

        Mockito.when(pecaService.buscaPeca(111L)).thenReturn(peca);

        MensagemDTO mensagemRetornada = vendaService.realizaVenda(vendaDto);
        MensagemDTO mensagemEsperada = new MensagemDTO("Quantidade inválida");

        Assert.assertEquals("Não deve realizar uma venda quando não há peças suficientes", mensagemEsperada,
                mensagemRetornada);
    }
}
