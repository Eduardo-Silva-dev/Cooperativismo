package br.com.sicredi.cooperativismo.repository;

import br.com.sicredi.cooperativismo.entity.Sessao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

    Page<Sessao> findAll(Pageable pageable);

}