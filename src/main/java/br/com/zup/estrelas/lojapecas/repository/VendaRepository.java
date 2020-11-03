package br.com.zup.estrelas.lojapecas.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.lojapecas.entity.Venda;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {

}
