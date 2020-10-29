package br.com.zup.estrelas.seujose.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.zup.estrelas.seujose.entity.Peca;

@Repository
public interface VendaRepository extends CrudRepository<Peca, Long> {

      Optional<Peca> findByNome(String nome);

      boolean existsByNome(String nome);

}
