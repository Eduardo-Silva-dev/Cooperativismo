package br.com.sicredi.cooperativismo.entity.mapper;

import br.com.sicredi.cooperativismo.controller.dto.request.AssociadoRequest;
import br.com.sicredi.cooperativismo.entity.Associado;
import br.com.sicredi.cooperativismo.controller.dto.response.AssociadoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {

    Associado toAssociado(AssociadoRequest associadoRequest);

    Associado toAssociadoByResponse(AssociadoResponse associadoResponse);

    AssociadoResponse toAssociadoResponse(Associado associado);
}
