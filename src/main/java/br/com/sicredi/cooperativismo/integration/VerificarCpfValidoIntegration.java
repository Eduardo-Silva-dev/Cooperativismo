package br.com.sicredi.cooperativismo.integration;

import br.com.sicredi.cooperativismo.integration.response.CpfStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VerificarCpfValido", url = "${verificar.cpf.url}")
public interface VerificarCpfValidoIntegration {

    @GetMapping("/{cep}")
    CpfStatus execute(@PathVariable("cep") String cep);
}
