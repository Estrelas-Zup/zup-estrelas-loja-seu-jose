package br.com.zup.estrelas.lojapecas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.lojapecas.entity.Venda;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long>{
	List<Venda> findByDataVenda(LocalDate dataVenda);
	List<Venda> findByDataVendaBetween (LocalDate dataAnoInicio, LocalDate dataAnoFim);
}