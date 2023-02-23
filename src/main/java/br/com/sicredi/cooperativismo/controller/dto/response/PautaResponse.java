package br.com.sicredi.cooperativismo.controller.dto.response;

import br.com.sicredi.cooperativismo.entity.Sessao;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class PautaResponse extends RepresentationModel<PautaResponse> {

    private long id;

    private String titulo;

    private String descricao;

    private Sessao sessao;

}
