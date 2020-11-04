package br.com.zup.estrelas.lojapecas.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.entity.Venda;
import br.com.zup.estrelas.lojapecas.repository.VendaRepository;

@Service
public class VendaService implements IVendaService {
	private static final String NOME_PASTA_VENDAS = "VendasLojaSeuJose";
	private static final String QUANTIDADE_EM_ESTOQUE_INSUFICIENTE = ("Quantidade em estoque insuficiente");
	private static final String PEÇA_INEXISTENTE = ("Peça inexistente");
	private static final String VENDA_REALIZADA_SUCESSO = ("Venda realizada com sucesso");

	@Autowired
	PecaService pecaService;

	@Autowired
	VendaRepository repository;

	public MensagemDTO realizaVenda(VendaDTO vendaDTO) {
		Optional<Peca> pecaOptional = pecaService.repository.findById(vendaDTO.getCodigoBarra());

		Optional<String> mensagemInvalida = this.validaPeca(pecaOptional, vendaDTO);

		if (mensagemInvalida.isPresent()) {
			return new MensagemDTO(mensagemInvalida.get());
		}

		alteraQuantidadeEstoquePeca(pecaOptional.get(), vendaDTO.getQuantidade());

		repository.save(montarObjetoVenda(pecaOptional.get(), vendaDTO.getQuantidade()));

		return new MensagemDTO(VENDA_REALIZADA_SUCESSO);
	}

	public void alteraQuantidadeEstoquePeca(Peca peca, int quantidade) {
		peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() - quantidade);
		pecaService.alteraPeca(peca.getCodigoBarra(), peca);
	}

	public Venda montarObjetoVenda(Peca peca, int quantidade) {
		Venda venda = new Venda();

		venda.setPrecoUnitario(peca.getPrecoVenda());
		venda.setQuantidade(quantidade);
		venda.setPrecoTotalVenda(quantidade * peca.getPrecoVenda());
		venda.setDataVenda(LocalDate.now());
		venda.setPeca(peca);

		return venda;
	}

	public Optional<String> validaPeca(Optional<Peca> pecaOptional, VendaDTO vendaDTO) {
		if (pecaOptional.isEmpty()) {
			return Optional.of(PEÇA_INEXISTENTE);
		}

		boolean quantidadeDisponivelEstoque = pecaOptional.get().getQuantidadeEstoque() < vendaDTO.getQuantidade();
		if (quantidadeDisponivelEstoque) {
			return Optional.of(QUANTIDADE_EM_ESTOQUE_INSUFICIENTE);
		}

		return Optional.empty();
	}

	public List<Venda> buscaVendasRealizadas() {
		return (List<Venda>) repository.findAll();
	}

	public List<Venda> buscaVendasPorData(LocalDate dataVenda) {
		return repository.findByDataVenda(dataVenda);
	}

	public List<Venda> buscaVendasPorAno(LocalDate dataAnoInicio, LocalDate dataAnoFim) {
		return repository.findByDataVendaBetween(dataAnoInicio, dataAnoFim);
	}

	public MensagemDTO gerarRelatorio() {
		File file = new File(NOME_PASTA_VENDAS);

		if (!file.exists()) {
			file.mkdir();
		}

		String nomeArquivoRelatorio = "/relatorio_" + file.listFiles().length + ".txt";
		String caminhoArquivo = NOME_PASTA_VENDAS + nomeArquivoRelatorio;
		
		try {
			FileWriter writer = new FileWriter(caminhoArquivo);
			writer.append(String.format("Código |\t Nome |\t Data venda | Quantidade |\t Preço unitario | Preço total\n\n"));
			List<Venda> vendasRealizadas = buscaVendasRealizadas();

			double precoTotalVendas = 0;
			
			for (Venda venda : vendasRealizadas) {				
				writer.append(String.format("\n%d\t %s\t %s\t %d\t\t R$%.2f\t R$%.2f", venda.getPeca().getCodigoBarra(), venda.getPeca().getNome(),
						venda.getDataVenda(), venda.getQuantidade(), venda.getPrecoUnitario(), venda.getPrecoTotalVenda()));
				
				precoTotalVendas += venda.getPrecoTotalVenda();
			}
			
			writer.append(String.format("\n\n\nVALOR TOTAL VENDAS: R$%.2f", precoTotalVendas));
			writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return new MensagemDTO("Erro ao armazenar arquivo");
		}

		return new MensagemDTO("Relatorio gerado com sucesso!");
	}
}
