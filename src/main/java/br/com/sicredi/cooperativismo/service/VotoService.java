package br.com.sicredi.cooperativismo.service;

import br.com.sicredi.cooperativismo.controller.dto.request.VotoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.CalcularVotacaoResponse;
import br.com.sicredi.cooperativismo.entity.Sessao;

public interface VotoService {

    void vote(VotoRequest votoRequest, Sessao sessao);

    CalcularVotacaoResponse calculateVotes(long idSessao);
}
