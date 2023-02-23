package br.com.sicredi.cooperativismo.controller.dto.response;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class AssociadoResponse extends RepresentationModel<AssociadoResponse> {

    private long id;

    private String cpf;
}
