package br.com.sicredi.cooperativismo.entity.mapper;

import br.com.sicredi.cooperativismo.controller.dto.request.SessaoRequest;
import br.com.sicredi.cooperativismo.entity.Sessao;
import br.com.sicredi.cooperativismo.controller.dto.response.SessaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessaoMapper {

    @Mapping(target = "dataFim", ignore = true)
    Sessao toSessao(SessaoRequest sessaoRequest);

    SessaoResponse toSessaoResponse(Sessao sessao);
}
