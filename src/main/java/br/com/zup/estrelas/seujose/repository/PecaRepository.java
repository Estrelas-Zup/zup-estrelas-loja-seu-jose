package br.com.zup.estrelas.seujose.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.seujose.entity.Peca;

@Repository
public interface PecaRepository extends CrudRepository<Peca, Long> {

}
