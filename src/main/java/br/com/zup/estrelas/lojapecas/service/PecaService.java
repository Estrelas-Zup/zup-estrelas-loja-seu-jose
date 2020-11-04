package br.com.zup.estrelas.lojapecas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;

@Service
public class PecaService implements IPecaService {
	private final static String PECA_CADASTRADA_COM_SUCESSO = ("Peca cadastrada com sucesso");
	private final static String PECA_ALTERADA_COM_SUCESSO = ("Peca alterada com sucesso");
	private final static String PECA_EXCLUIDA_COM_SUCESSO = ("Peca excluida com sucesso");
	private final static String PECA_INEXISTENTE = ("Peça inexistente, não foi possivel realizar a operação");
	private final static String PECA_NAO_CADASTRADA = ("Nao foi possivel cadastrar peça");

	@Autowired
	PecaRepository repository;

	public MensagemDTO adicionaPeca(Peca peca) {
		if (repository.existsById(peca.getCodigoBarra())) {
			return new MensagemDTO(PECA_NAO_CADASTRADA);
		}

		repository.save(peca);
		return new MensagemDTO(PECA_CADASTRADA_COM_SUCESSO);
	}

	public MensagemDTO removePeca(Long codigoBarra) {
		if (repository.existsById(codigoBarra)) {
			repository.deleteById(codigoBarra);
			return new MensagemDTO(PECA_EXCLUIDA_COM_SUCESSO);
		}
		return new MensagemDTO(PECA_INEXISTENTE);
	}

	public MensagemDTO alteraPeca(Long codigoBarra, Peca peca) {
		if (repository.existsById(codigoBarra) && codigoBarra.equals(peca.getCodigoBarra())) {
			repository.save(peca);
			return new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
		}

		return new MensagemDTO(PECA_INEXISTENTE);
	}

	public Peca buscaPeca(Long codigoBarra) {
		return repository.findById(codigoBarra).get();
	}

	public List<Peca> listarPecas() {
		return (List<Peca>) repository.findAll();
	}

	public List<Peca> buscarPecaPorNome(String nome) {
		return repository.findByNomeLike(nome);
	}

	public List<Peca> buscarPecaPorModelo(String modelo) {
		return repository.findByModelo(modelo);
	}

	public List<Peca> buscarPecaPorCategoria(String categoria) {
		return repository.findByCategoria(categoria);
	}
}
