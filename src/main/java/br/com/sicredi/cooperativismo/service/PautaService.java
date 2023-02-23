package br.com.sicredi.cooperativismo.service;

import br.com.sicredi.cooperativismo.controller.dto.request.PautaRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.PautaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PautaService {

    PautaResponse findById (long id);

    Page<PautaResponse> findAll(Pageable pageable);

    void insert(PautaRequest pautaRequest);

    void update(PautaRequest pautaRequest, Long idPauta);

    void delete(long id);
}