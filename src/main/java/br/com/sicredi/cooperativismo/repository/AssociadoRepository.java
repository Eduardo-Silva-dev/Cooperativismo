package br.com.sicredi.cooperativismo.repository;

import br.com.sicredi.cooperativismo.entity.Associado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Page<Associado> findAll(Pageable pageable);

    Optional<Associado> findByCpf(@Param("cpf") String cpf);
}
