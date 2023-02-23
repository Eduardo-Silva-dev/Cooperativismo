package br.com.sicredi.cooperativismo.repository;

import br.com.sicredi.cooperativismo.entity.Pauta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

    Page<Pauta> findAll(Pageable pageable);

}
