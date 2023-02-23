package br.com.sicredi.cooperativismo.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
public class SessaoResponse extends RepresentationModel<SessaoResponse> {

    private long id;

    private String titulo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataInicio;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataFim;

}
