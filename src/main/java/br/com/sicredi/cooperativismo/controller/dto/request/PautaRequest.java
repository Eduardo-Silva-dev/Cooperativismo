package br.com.sicredi.cooperativismo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PautaRequest{

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

}
