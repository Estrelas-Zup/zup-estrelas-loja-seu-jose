package br.com.zup.estrelas.lojapecas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.enums.Categoria;
import br.com.zup.estrelas.lojapecas.repository.PecaRepository;

@Service
public class PecaService implements IPecaService {

    private static final String PECA_ALTERADA_COM_SUCESSO = "Peça alterada com sucesso.";
    private static final String PECA_REMOVIDA_COM_SUCESSO = "Peça removida com sucesso!";
    private static final String PECA_JA_CADASTRADA = "O cadastro não ocorreu, peça já está cadastrada";
    private static final String CADASTRO_REALIZADO_COM_SUCESSO = "Cadastro realizado com sucesso.";
    private static final String PECA_INEXISTENTE = "Peça inexistente.";

    @Autowired
    PecaRepository pecaRepository;

    public MensagemDTO adicionaPeca(Peca peca) {

    	boolean pecaExiste = pecaRepository.existsById(peca.getCodBarras());
        if (pecaExiste) {
            return new MensagemDTO(PECA_JA_CADASTRADA);
        }

        pecaRepository.save(peca);
        return new MensagemDTO(CADASTRO_REALIZADO_COM_SUCESSO);
    }

    public Peca buscaPeca(Long codBarras) {
        return pecaRepository.findById(codBarras).get();
    }

    public List<Peca> listaPecas() {
        return (List<Peca>) pecaRepository.findAll();
    }

    public MensagemDTO removePeca(Long codBarras) {
    	
    	boolean pecaExiste = pecaRepository.existsById(codBarras);
        if (pecaExiste) {
            pecaRepository.deleteById(codBarras);
            return new MensagemDTO(PECA_REMOVIDA_COM_SUCESSO);
        }

        return new MensagemDTO(PECA_INEXISTENTE);
    }

    public MensagemDTO alteraPeca(Long codBarras, AlteraPecaDTO alteraPecaDTO) {

        Optional<Peca> pecaConsultada = pecaRepository.findById(codBarras);

        boolean pecaExiste = pecaConsultada.isPresent();
        if (pecaExiste) {

            Peca pecaAlterada = pecaConsultada.get();

            pecaAlterada.setCategoria(alteraPecaDTO.getCategoria());
            pecaAlterada.setFabricante(alteraPecaDTO.getFabricante());
            pecaAlterada.setModelo(alteraPecaDTO.getModelo());
            pecaAlterada.setNome(alteraPecaDTO.getNome());
            pecaAlterada.setPrecoCusto(alteraPecaDTO.getPrecoCusto());
            pecaAlterada.setPrecoVenda(alteraPecaDTO.getPrecoVenda());
            pecaAlterada.setQtdEstoque(alteraPecaDTO.getQtdEstoque());

            pecaRepository.save(pecaAlterada);
            return new MensagemDTO(PECA_ALTERADA_COM_SUCESSO);
        }

        return new MensagemDTO(PECA_INEXISTENTE);
    }
    
    
    public List<Peca> buscarPecaByNome(String nome) {
		return pecaRepository.findByNomeLike(nome);
	}

	public List<Peca> buscarPecaByModelo(String modelo) {
		return pecaRepository.findByModelo(modelo);
	}

	public List<Peca> buscarPecaByCategoria(Categoria categoria) {
		return pecaRepository.findByCategoria(categoria);
	}

}
