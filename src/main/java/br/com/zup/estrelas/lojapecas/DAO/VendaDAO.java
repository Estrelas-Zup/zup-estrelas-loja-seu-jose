package br.com.zup.estrelas.lojapecas.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.zup.estrelas.lojapecas.dto.VendaRelatorioDTO;

@Component
public class VendaDAO {

	
	private List<VendaRelatorioDTO> vendas;

    public VendaDAO() {
        this.vendas = new ArrayList<VendaRelatorioDTO>();
    }

    public void insereVenda(VendaRelatorioDTO venda) {
        this.vendas.add(venda);
    }

    public List<VendaRelatorioDTO> getVendas() {
        return vendas;
    }
}
