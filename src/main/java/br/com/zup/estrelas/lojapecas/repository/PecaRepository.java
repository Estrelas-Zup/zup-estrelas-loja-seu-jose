package br.com.zup.estrelas.lojapecas.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.zup.estrelas.lojapecas.entity.Peca;

@Repository
public interface PecaRepository extends CrudRepository<Peca, Long> {
	List<Peca> findByNomeLike(String nome);
	List<Peca> findByModelo(String modelo);
	List<Peca> findByCategoria(String categoria);
}