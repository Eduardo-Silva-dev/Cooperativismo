package br.com.sicredi.cooperativismo.service.impl;

import br.com.sicredi.cooperativismo.controller.dto.request.AssociadoRequest;
import br.com.sicredi.cooperativismo.controller.dto.request.VotoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.CalcularVotacaoResponse;
import br.com.sicredi.cooperativismo.controller.exception.DataIntegrityException;
import br.com.sicredi.cooperativismo.entity.Associado;
import br.com.sicredi.cooperativismo.entity.Sessao;
import br.com.sicredi.cooperativismo.entity.Voto;
import br.com.sicredi.cooperativismo.entity.enums.CpfValidoStatusEnum;
import br.com.sicredi.cooperativismo.entity.mapper.AssociadoMapper;
import br.com.sicredi.cooperativismo.integration.VerificarCpfValidoIntegration;
import br.com.sicredi.cooperativismo.integration.response.CpfStatus;
import br.com.sicredi.cooperativismo.repository.VotoRepository;
import br.com.sicredi.cooperativismo.service.AssociadoService;
import br.com.sicredi.cooperativismo.service.SessaoService;
import br.com.sicredi.cooperativismo.service.VotoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VotoServiceImpl implements VotoService {

    Logger log = LogManager.getLogger(VotoServiceImpl.class);

    private final VotoRepository votoRepository;

    private final SessaoService sessaoService;

    private final AssociadoService associadoService;

    private final AssociadoMapper associadoMapper;

    private final VerificarCpfValidoIntegration verificarCpfValidoIntegration;


    @Override
    @Transactional
    public void vote(VotoRequest votoRequest, Sessao sessao){
        log.info("[VotoServiceImpl*vote] - Inicio do Processo de votação!");

        this.sessaoService.verificarTempoSessao(sessao);

//        this.verificarCpfValido(votoRequest.getCpf());

        Associado associado = this.verificarSeAssociadoJaVotou(votoRequest.getCpf());

        Voto voto = Voto.builder()
                .associado(associado)
                .status(votoRequest.getStatus().equalsIgnoreCase("SIM") ? 1 : 0)
                .sessao(sessao)
                .build();

        votoRepository.save(voto);
        log.info("[VotoServiceImpl*vote] - Voto salvo com sucesso!");
        log.info("[VotoServiceImpl*vote] - Fim do Processo de salvar Voto!");
    }

    @Override
    public CalcularVotacaoResponse calculateVotes(long idSessao){
        log.info("[VotoServiceImpl*calculateVotes] - Inicio do Processo de calcular votos de uma sessão!");
        Optional<Sessao> sessao = sessaoService.findById(idSessao);
        CalcularVotacaoResponse votoResponse = votoRepository.calculateVotes(sessao.get().getId());
        votoResponse.setIdSessao(sessao.get().getId());
        votoResponse.setIdPauta(sessao.get().getPauta().getId());
        log.info("[VotoServiceImpl*calculateVotes] - Calculado votos com sucesso!");
        log.info("[VotoServiceImpl*calculateVotes] - Fim do Processo de calcular votos de uma sessão!");
        return votoResponse;
    }

    private CpfStatus verificarCpfValido(String cpf){
        log.info("[VotoServiceImpl*verificarCpfValido] - Inicio do Processo de verificar sCpf valido!");

        CpfStatus cpfStatus = verificarCpfValidoIntegration.execute(cpf);
        if (cpfStatus.equals(CpfValidoStatusEnum.IMCAPAZ)){
            log.error("[VotoServiceImpl*verificarCpfValido] - Associado imcapaz de votar!");
            throw new DataIntegrityException("Associado imcapaz de votar! Tipo: " + Associado.class.getName());
        }

        log.info("[VotoServiceImpl*verificarCpfValido] - Fim do Processo de verificar Cpf valido!");
        return cpfStatus;
    }

    private Associado verificarSeAssociadoJaVotou(String cpf) {
        log.info("[VotoServiceImpl*verificarSeAssociadoJaVotou] - Inicio do Processo de verificar se Associado já votou!");

        Associado associado = associadoService.findByCpf(cpf).orElse(null);
        if(associado == null){
            return associadoMapper.toAssociadoByResponse(
                            associadoService.insert(new AssociadoRequest(cpf)));
        }

        votoRepository.findVotoByAssociado(associado).orElseThrow(() -> new DataIntegrityException("Associado já votou! Tipo: " + Associado.class.getName()));

        log.info("[VotoServiceImpl*verificarSeAssociadoJaVotou] - Fim do Processo de verificar se Associado já votou!");
        return associado;
    }

}
