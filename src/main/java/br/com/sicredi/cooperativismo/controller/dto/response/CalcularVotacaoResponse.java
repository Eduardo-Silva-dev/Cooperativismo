package br.com.sicredi.cooperativismo.controller.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalcularVotacaoResponse extends RepresentationModel<CalcularVotacaoResponse> {

    private long idPauta;

    private long idSessao;

    private Integer votosSim;

    private Integer votosNao;

    public CalcularVotacaoResponse(Integer votosSim, Integer votosNao){
        this.votosSim = votosSim;
        this.votosNao = votosNao;
    }

}
