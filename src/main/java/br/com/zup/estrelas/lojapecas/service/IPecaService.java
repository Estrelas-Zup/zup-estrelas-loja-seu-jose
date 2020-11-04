package br.com.zup.estrelas.lojapecas.service;

import java.util.List;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;

public interface IPecaService {

    public MensagemDTO adicionaPeca(Peca peca);

    public Peca buscaPeca(Long codBarras);

    public List<Peca> listaPecas();

    public MensagemDTO removePeca(Long codBarras);

    public MensagemDTO alteraPeca(Long codBarras, AlteraPecaDTO peca);
    
    public List<Peca> buscarPecaByNome(String nome);
	
	public List<Peca> buscarPecaByModelo(String modelo);
	
	public List<Peca> buscarPecaByCategoria(Categoria categoria);

}
