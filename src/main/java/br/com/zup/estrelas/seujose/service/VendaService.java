package br.com.zup.estrelas.seujose.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.seujose.dto.AlteraPecaDTO;
import br.com.zup.estrelas.seujose.dto.MensagemDTO;
import br.com.zup.estrelas.seujose.dto.VendaDTO;
import br.com.zup.estrelas.seujose.dto.VendaRelatorioDTO;
import br.com.zup.estrelas.seujose.entity.Peca;
import br.com.zup.estrelas.seujose.entity.Venda;
import br.com.zup.estrelas.seujose.repository.VendaRepository;

@Service
public class VendaService {

	private static final String QUANTIDADE_INSUFUCIENTE = "QUANTIDADE EM ESTOQUE INSUFICIENTE!";
	private static final String PECA_ADQUIRIDA_COM_SUCESSO = "PECA ADQUIRIDA COM SUCESSO!";
	private static final String PECA_NÃO_ENCONTRADA = "PEÇA NÃO ENCONTRADA PELO CODIGO DE BARRAS!";
	private static final String ERRO_AO_PROCESSAR_ARQUIVO = "Erro ao processar arquivo.";

	@Autowired
	VendaRepository repository;

	@Autowired
	PecaService pecaService;

	public List<VendaRelatorioDTO> listaVendas() {

		List<Venda> vendas = (List<Venda>) this.repository.findAll();
		List<VendaRelatorioDTO> vendasDTO = new ArrayList<VendaRelatorioDTO>();

		for (Venda venda : vendas) {
			VendaRelatorioDTO vendaDTO = new VendaRelatorioDTO();
			BeanUtils.copyProperties(venda, vendaDTO);
			Peca peca = venda.getPeca();
			vendaDTO.setNome(peca.getNome());
			vendaDTO.setCodBarras(peca.getCodBarras());
			vendasDTO.add(vendaDTO);
		}

		return vendasDTO;

	}

	public MensagemDTO realizaVenda(VendaDTO venda) throws IOException {

		Optional<Peca> peca = Optional.of(pecaService.listaPecaPorCodBarras(venda.getCodBarras()));
		MensagemDTO mensagemInvalida = verificaCondicoesDeVenda(peca, venda);

		if (mensagemInvalida != null) {
			return new MensagemDTO(mensagemInvalida.getMensagem());
		}

		Peca pecaASerComprada = peca.get();

		this.armazenaVenda(pecaASerComprada, venda);

		this.atualizaEstoqueDePeca(pecaASerComprada, venda);

		return new MensagemDTO(PECA_ADQUIRIDA_COM_SUCESSO);
	}

	public MensagemDTO geraRelatorioDeVenda() {

		File diretorio = this.criaDiretorio();

		int numeroDeArquivos = diretorio.listFiles().length + 1;
		FileWriter escritaRelatorio;

		try {
			escritaRelatorio = new FileWriter(diretorio.getPath() + "/Relatorio " + numeroDeArquivos + ".txt");
			escritaRelatorio.append(String.format("CodBarras\t\tnome\t\tQuantidade\t\tValor"));

			List<VendaRelatorioDTO> relatorioVendas = this.listaVendas();

			Double valorTotalDiario = 0D;
			for (VendaRelatorioDTO venda : relatorioVendas) {
				escritaRelatorio.append(String.format("\n%d\t\t%s\t\t%d\t\t%.2f", venda.getCodBarras(), venda.getNome(),
						venda.getQuantidade(), venda.getValor()));
				valorTotalDiario += venda.getValor();
			}

			escritaRelatorio.append(String.format("\n\t\t\t Valor Total: %.2f", valorTotalDiario));
			escritaRelatorio.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new MensagemDTO(ERRO_AO_PROCESSAR_ARQUIVO);
		}

		return null;
	}

	private File criaDiretorio() {
		File diretorio = new File("Relatorio De Vendas");
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}

		return diretorio;
	}

	private MensagemDTO verificaCondicoesDeVenda(Optional<Peca> peca, VendaDTO venda) {
		if (peca.isEmpty()) {
			return new MensagemDTO(PECA_NÃO_ENCONTRADA);
		}

		if (peca.get().getQuantidadeEmEstoque() < venda.getQtdAComprar()) {
			return new MensagemDTO(QUANTIDADE_INSUFUCIENTE);
		}

		return null;
	}

	private void armazenaVenda(Peca pecaASerComprada, VendaDTO vendaDTO) {

		Venda vendaDb = new Venda();
		vendaDb.setPeca(pecaASerComprada);
		vendaDb.setQtdAComprar(vendaDTO.getQtdAComprar());
		Double valorVenda = vendaDTO.getQtdAComprar() * pecaASerComprada.getPrecoDeVenda();
		vendaDb.setValor(valorVenda);
		vendaDb.setNome(pecaASerComprada.getNome());
		vendaDb.setDataVenda(LocalDate.now());

		repository.save(vendaDb);

	}

	private void atualizaEstoqueDePeca(Peca pecaASerComprada, VendaDTO venda) {
		int qtdEstoqueAtualizado = pecaASerComprada.getQuantidadeEmEstoque() - venda.getQtdAComprar();
		AlteraPecaDTO pecaAlterada = new AlteraPecaDTO();
		BeanUtils.copyProperties(pecaASerComprada, pecaAlterada);
		pecaAlterada.setQuantidadeEmEstoque(qtdEstoqueAtualizado);
		pecaService.alteraPeca(pecaASerComprada.getCodBarras(), pecaAlterada);
	}

}
