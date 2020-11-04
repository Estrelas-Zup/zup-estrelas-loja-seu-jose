package br.com.zup.estrelas.lojapecas.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.estrelas.lojapecas.DAO.VendaDAO;
import br.com.zup.estrelas.lojapecas.dto.AlteraPecaDTO;
import br.com.zup.estrelas.lojapecas.dto.MensagemDTO;
import br.com.zup.estrelas.lojapecas.dto.VendaRelatorioDTO;
import br.com.zup.estrelas.lojapecas.entity.Peca;
import br.com.zup.estrelas.lojapecas.entity.Venda;

@Service
public class VendaService implements IVendaService {
	
	 @Autowired
	    VendaDAO vendaDao;

	    @Autowired
	    PecaService pecaService;

	    @Override
	    public MensagemDTO realizaVenda(Venda venda) {

	        Peca peca = pecaService.buscaPeca(venda.getReferencia());

	        Optional<String> mensagemInvalida = this.validaVenda(peca, venda);
	        if (mensagemInvalida.isPresent()) {
	            return new MensagemDTO(mensagemInvalida.get());
	        }

	        this.armazenaVenda(peca, venda);
	        this.alteraEstoque(peca, venda);

	        return new MensagemDTO("Venda realizada com sucesso.");
	    }

	    @Override
	    public MensagemDTO geraRelatorio() {

	        File diretorio = this.criaDiretorio();

	        int numeroArquivos = diretorio.listFiles().length + 1;
	        FileWriter arquivoRelatorio;

	        try {
	            String caminhoArquivo = diretorio.getPath() + "/relatorio_" + numeroArquivos + ".txt";
	            arquivoRelatorio = new FileWriter(caminhoArquivo);
	            arquivoRelatorio.append(String.format("Código\tNome\tQuantidade\tValor"));

	            List<VendaRelatorioDTO> relatorioDia = vendaDao.getVendas();

	            Double valorTotal = (double) 0;
	            for (VendaRelatorioDTO venda : relatorioDia) {
	                arquivoRelatorio.append(String.format("\n%d\t%s\t%d\t%.2f", venda.getReferencia(), venda.getNome(),
	                        venda.getQuantidade(), venda.getValor()));
	                valorTotal += venda.getValor();
	            }

	            arquivoRelatorio.append(String.format("\n\t\t\t Valor Total: %.2f", valorTotal));
	            arquivoRelatorio.close();

	        } catch (IOException e) {
	            System.err.println(e.getMessage());
	            return new MensagemDTO("Erro ao processar arquivo.");
	        }

	        return new MensagemDTO("Relatório gerado com sucesso :)");
	    }

	    @Override
	    public List<VendaRelatorioDTO> listaVendas() {
	        return this.vendaDao.getVendas();
	    }

	    private Optional<String> validaVenda(Peca peca, Venda venda) {
	        if (Objects.isNull(peca)) {
	            return Optional.of("Peça inexistente");
	        }
	        boolean qtdVendaMenorEstoque = peca.getQtdEstoque() < venda.getQuantidade();
	        if (qtdVendaMenorEstoque) {
	            return Optional.of("Quantidade inválida");
	        }

	        return Optional.empty();
	    }

	    private void armazenaVenda(Peca peca, Venda venda) {
	        VendaRelatorioDTO vendaRelatorio = new VendaRelatorioDTO();
	        vendaRelatorio.setReferencia(venda.getReferencia());
	        vendaRelatorio.setNome(peca.getNome());
	        vendaRelatorio.setQuantidade(venda.getQuantidade());

	        Double valorVenda = venda.getQuantidade() * peca.getPrecoVenda();
	        vendaRelatorio.setValor(valorVenda);
	        vendaDao.insereVenda(vendaRelatorio);
	    }

	    private void alteraEstoque(Peca peca, Venda venda) {
	        int novaQtdPecas = peca.getQtdEstoque() - venda.getQuantidade();
	        AlteraPecaDTO pecaAlterada = new AlteraPecaDTO();
	        BeanUtils.copyProperties(peca, pecaAlterada);
	        pecaAlterada.setQtdEstoque(novaQtdPecas);
	        pecaService.alteraPeca(peca.getCodBarras(), pecaAlterada);
	    }

	    private File criaDiretorio() {

	        File diretorio = new File("relatorios");
	        if (!diretorio.exists()) {
	            diretorio.mkdir();
	        }

	        return diretorio;
	    }
	
}
