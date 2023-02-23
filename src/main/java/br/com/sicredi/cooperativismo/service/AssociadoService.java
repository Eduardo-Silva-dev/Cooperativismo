package br.com.sicredi.cooperativismo.service;

import br.com.sicredi.cooperativismo.controller.dto.request.AssociadoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.AssociadoResponse;
import br.com.sicredi.cooperativismo.entity.Associado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssociadoService {

    Optional<Associado> findById (long id);

    Page<AssociadoResponse> findAll(Pageable pageable);

    AssociadoResponse insert(AssociadoRequest associadoRequest);

    AssociadoResponse update(AssociadoRequest associadoRequest, Long idAssociado);

    void delete(long id);

    AssociadoResponse toLinks(AssociadoResponse associadoResponse);

    Optional<Associado> findByCpf(String cpf);

}
