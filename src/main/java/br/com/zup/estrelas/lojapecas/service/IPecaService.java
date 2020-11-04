package br.com.zup.estrelas.lojapecas.service;

import java.util.List;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;

public interface IPecaService {
	public MensagemDTO adicionaPeca(Peca peca);
	
	public MensagemDTO removePeca(Long codigoBarra);
	
	public MensagemDTO alteraPeca(Long codigoBarra, Peca peca);
	
	public Peca buscaPeca(Long codigoBarra);
	
	public List<Peca> listarPecas();
	
	public List<Peca> buscarPecaPorNome(String nome);
	
	public List<Peca> buscarPecaPorModelo(String modelo);
	
	public List<Peca> buscarPecaPorCategoria(String categoria);
}
