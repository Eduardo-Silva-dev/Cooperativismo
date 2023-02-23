package br.com.sicredi.cooperativismo.repository;

import br.com.sicredi.cooperativismo.controller.dto.response.CalcularVotacaoResponse;
import br.com.sicredi.cooperativismo.entity.Associado;
import br.com.sicredi.cooperativismo.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query(name = "Voto.calculateVotes", nativeQuery = true)
    CalcularVotacaoResponse calculateVotes(@Param("idSessao") long idSessao);

    Optional<Voto> findVotoByAssociado(Associado associado);

}