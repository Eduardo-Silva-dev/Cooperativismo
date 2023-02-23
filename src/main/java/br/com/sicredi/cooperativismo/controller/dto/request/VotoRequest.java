package br.com.sicredi.cooperativismo.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class VotoRequest {

    @NotBlank
    private String cpf;

    @NotNull
    private Long idSessao;

    @NotBlank
    private String status;

}
