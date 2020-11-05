package br.com.zup.estrelas.seujose.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.seujose.entity.Venda;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {


}
