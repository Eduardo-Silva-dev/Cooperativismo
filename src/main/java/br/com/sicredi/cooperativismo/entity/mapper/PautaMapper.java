package br.com.sicredi.cooperativismo.entity.mapper;

import br.com.sicredi.cooperativismo.controller.dto.request.PautaRequest;
import br.com.sicredi.cooperativismo.entity.Pauta;
import br.com.sicredi.cooperativismo.controller.dto.response.PautaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaMapper {

    Pauta toPauta(PautaRequest pautaRequest);

    PautaResponse toPautaResponse(Pauta pauta);
}
