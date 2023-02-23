package br.com.sicredi.cooperativismo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class SessaoRequest {

    @NotBlank
    private String titulo;

    @NotNull
    private long idPauta;

    private Integer dataFim;

}
