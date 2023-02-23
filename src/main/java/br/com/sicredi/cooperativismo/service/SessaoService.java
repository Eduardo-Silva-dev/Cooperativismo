package br.com.sicredi.cooperativismo.service;

import br.com.sicredi.cooperativismo.controller.dto.request.SessaoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.SessaoResponse;
import br.com.sicredi.cooperativismo.entity.Sessao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SessaoService {

    Optional<Sessao> findById (long id);

    Page<SessaoResponse> findAll(Pageable pageable);

    void insert(SessaoRequest sessaoRequest);

    void update(SessaoRequest sessaoRequest, Long idSessao);

    void delete(long id);

    SessaoResponse toLinks(SessaoResponse sessaoDto);

    Sessao verificarTempoSessao(Sessao sessao);
}
