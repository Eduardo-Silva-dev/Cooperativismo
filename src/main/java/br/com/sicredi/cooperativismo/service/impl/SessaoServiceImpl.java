package br.com.sicredi.cooperativismo.service.impl;

import br.com.sicredi.cooperativismo.controller.SessaoController;
import br.com.sicredi.cooperativismo.controller.dto.request.SessaoRequest;
import br.com.sicredi.cooperativismo.controller.exception.DataIntegrityException;
import br.com.sicredi.cooperativismo.controller.exception.NotFoundExeption;
import br.com.sicredi.cooperativismo.entity.Sessao;
import br.com.sicredi.cooperativismo.controller.dto.response.SessaoResponse;
import br.com.sicredi.cooperativismo.repository.PautaRepository;
import br.com.sicredi.cooperativismo.repository.SessaoRepository;
import br.com.sicredi.cooperativismo.service.SessaoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import br.com.sicredi.cooperativismo.entity.mapper.SessaoMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@RequiredArgsConstructor
public class SessaoServiceImpl implements SessaoService {

    Logger log = LogManager.getLogger(SessaoServiceImpl.class);

    private final SessaoRepository sessaoRepository;

    private final PautaRepository pautaRepository;

    private final SessaoMapper sessaoMapper;

    @Override
    public Optional<Sessao> findById (long id){
        log.info("[SessaoServiceImpl*findById] - Inicio do Processo de Buscar Sessão por ID!");
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        if(!sessao.isPresent()){
            log.error("[SessaoServiceImpl*findById] - Sessão não encontrada!");
            throw new NotFoundExeption("Sessão não encontrada! Id: " + id + ", Tipo: " + Sessao.class.getName());
        }
        log.info("[SessaoServiceImpl*findById] - Buscar Sessão por ID com sucesso!");
        log.info("[SessaoServiceImpl*findById] - Fim do Processo de Buscar Sessão por ID!");
        return sessao;
    }

    @Override
    public Page<SessaoResponse> findAll(Pageable pageable){
        log.info("[SessaoServiceImpl*findAll] - Inicio do Processo de Buscar todos os Sessão!");
        Page<Sessao> listSessao = sessaoRepository.findAll(pageable);
        List<SessaoResponse> listSessaoResponse = listSessao.stream()
                                                    .map(sessao -> sessaoMapper.toSessaoResponse(sessao))
                                                    .map(sessao -> toLinks(sessao))
                                                    .collect(Collectors.toList());
        log.info("[SessaoServiceImpl*findAll] - Buscar todos os Sessão com sucesso!");
        log.info("[SessaoServiceImpl*findAll] - Fim do Processo de Buscar todos os Sessão!");
        return new PageImpl<>(listSessaoResponse);
    }

    @Override
    @Transactional
    public void insert(SessaoRequest sessaoRequest){
            log.info("[SessaoServiceImpl*insert] - Inicio do Processo de salvar Sessão!");
            Sessao sessao = sessaoMapper.toSessao(sessaoRequest);
            sessao.setPauta(pautaRepository.findById(sessaoRequest.getIdPauta()).get());

            sessao = this.processarDatasSessao(sessaoRequest, sessao);
        try {
            sessaoRepository.save(sessao);
            log.info("[SessaoServiceImpl*insert] - Sessão salvo com sucesso!");
            log.info("[SessaoServiceImpl*insert] - Fim do Processo de salvar Sessão!");
        } catch (Exception e) {
            log.error("[SessaoServiceImpl*insert] - Erro no Processo de salvar Sessão! - {}", e.getMessage());
            throw new RuntimeException("[SessaoServiceImpl*insert] - Erro no Processo de salvar Sessão! - " + e.getMessage());
        }
    }

    private Sessao processarDatasSessao(SessaoRequest sessaoRequest, Sessao sessao) {
        try {
            log.info("[SessaoServiceImpl*processarDatasSessao] - Inicio do Processo da construir datas Sessão!");
            Calendar dataInicial = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
            sessao.setDataInicio(dataInicial.getTime());
            Date dataFinal = null;
            if(sessaoRequest.getDataFim() == null || sessaoRequest.getDataFim() < 1) {
                log.info("[SessaoServiceImpl*processarDatasSessao] - Tempo final de 1 minuto!");
                dataInicial.add(Calendar.MINUTE, 1);
                dataFinal = dataInicial.getTime();
                sessao.setDataFim(dataFinal);
            } else {
                dataInicial.add(Calendar.MINUTE, sessaoRequest.getDataFim());
                log.info("[SessaoServiceImpl*processarDatasSessao] - Tempo final de " + sessaoRequest.getDataFim() + " minuto!");
                dataFinal = dataInicial.getTime();
                sessao.setDataFim(dataFinal);
            }
            log.info("[SessaoServiceImpl*processarDatasSessao] - Processdo de construir datas Sessão com sucesso!");
            log.info("[SessaoServiceImpl*processarDatasSessao] - Fim do Processo da construir datas Sessãoo!");
            return sessao;
        } catch (Exception e) {
            log.error("[SessaoServiceImpl*processarDatasSessao] - Erro no Processo da construir datas Sessão! - {}", e.getMessage());
            throw new RuntimeException("[SessaoServiceImpl*processarDatasSessao] - Erro no Processo da construir datas Sessão! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(SessaoRequest sessaoRequest, Long idSessao){
            log.info("[SessaoServiceImpl*update] - Inicio do Processo de update Sessão!");
            Sessao sessao = sessaoMapper.toSessao(sessaoRequest);
            sessao.setId(idSessao);
            sessao.setPauta(pautaRepository.findById(sessaoRequest.getIdPauta()).get());
        try {
            sessaoRepository.save(sessao);
            log.info("[SessaoServiceImpl*update] - Sessão atualizado com sucesso!");
            log.info("[SessaoServiceImpl*update] - Fim do Processo de update Sessão!");
        } catch (Exception e) {
            log.error("[SessaoServiceImpl*update] - Erro no Processo de atualizar Sessão! - {}", e.getMessage());
            throw new RuntimeException("[SessaoServiceImpl*update] - Erro no Processo de atualizar Sessão! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(long id){
            log.info("[SessaoServiceImpl*delete] - Inicio do Processo de delete Sessão!");
            findById(id);
        try {
            sessaoRepository.deleteById(id);
            log.info("[SessaoServiceImpl*delete] - Sessão deletado com sucesso!");
            log.info("[SessaoServiceImpl*delete] - Fim do Processo de delete Sessão!");
        } catch (Exception e) {
            log.error("[SessaoServiceImpl*delete] - Erro no Processo de deletar Sessão! - {}", e.getMessage());
            throw new RuntimeException("[SessaoServiceImpl*delete] - Erro no Processo de deletar Sessão! - " + e.getMessage());
        }
    }

    @Override
    public Sessao verificarTempoSessao(Sessao sessao){
        log.info("[SessaoServiceImpl*verificarTempoSessao] - Inicio do Processo de verificar tempo sessão!");

        if(sessao.getDataFim().getTime() < new Date().getTime()){
            log.error("[SessaoServiceImpl*verificarTempoSessao] - Sessão já inspirou!");
            throw new DataIntegrityException("Sessão não encontrada! Tipo: " + Sessao.class.getName());
        }

        log.info("[SessaoServiceImpl*verificarTempoSessao] - Fim do Processo de verificar tempo sessão!");
        return sessao;
    }

    public SessaoResponse toLinks(SessaoResponse sessaoDto){
        try {
            log.info("[SessaoServiceImpl*toLinks] - Inicio do Processo de Links Hateoas!");
    
            Pageable pageable = (Pageable) PageRequest.of(0, 10);
    
            sessaoDto.add(linkTo(methodOn(SessaoController.class)
                    .findSessaoById(sessaoDto.getId())).withSelfRel());
    
            sessaoDto.add(linkTo(methodOn(SessaoController.class)
                    .findAllSessao(pageable)).withRel("Lista de Sessões"));
    
            sessaoDto.add(linkTo(methodOn(SessaoController.class)
                    .updateSessao(sessaoDto.getId(), new SessaoRequest())).withRel("Update"));
    
            sessaoDto.add(linkTo(methodOn(SessaoController.class)
                    .deleteSessao(sessaoDto.getId())).withRel("Delete"));
    
            log.info("[SessaoServiceImpl*toLinks] - Fim do Processo de Links Hateoas!");
            return sessaoDto;
        } catch (Exception e) {
            log.error("[SessaoServiceImpl*toLinks] - Erro no Processo de criar links Sessão! - {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}
