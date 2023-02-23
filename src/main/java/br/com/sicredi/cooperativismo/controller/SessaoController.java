package br.com.sicredi.cooperativismo.controller;

import br.com.sicredi.cooperativismo.controller.dto.request.SessaoRequest;
import br.com.sicredi.cooperativismo.controller.dto.request.VotoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.SessaoResponse;
import br.com.sicredi.cooperativismo.controller.dto.response.CalcularVotacaoResponse;
import br.com.sicredi.cooperativismo.entity.Sessao;
import br.com.sicredi.cooperativismo.entity.mapper.SessaoMapper;
import br.com.sicredi.cooperativismo.service.SessaoService;
import br.com.sicredi.cooperativismo.service.VotoService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${url.apiPrefix}${url.sessao}")
public class SessaoController {

    private final SessaoService sessaoService;

    private final VotoService votoService;

    private final SessaoMapper sessaoMapper;

    @GetMapping
    public ResponseEntity<Page<SessaoResponse>> findAllSessao(@PageableDefault(size = 10, page = 0, sort = "titulo") Pageable pageable){
        log.info("[SessaoController*findAllSessao] - Inicio da Requisição de buscar todos os Sessão!");
        Page<SessaoResponse> listSessaoDto = sessaoService.findAll(pageable);
        log.info("[SessaoController*findAllSessao] - Fim da Requisição de buscar todos os Sessão!");
        return ResponseEntity.ok().body(listSessaoDto);
    }


    @GetMapping("${url.sessao.calcular-votos-da-sessao}/{idSessao}")
    public ResponseEntity<CalcularVotacaoResponse> calcularVotosDaSessao(@PathVariable Long idSessao){
        log.info("[SessaoController*calcularVotosDaSessao] - Inicio da Requisição de calcular votos da Sessão!");
        CalcularVotacaoResponse votoResponse = votoService.calculateVotes(idSessao);
        log.info("[SessaoController*calcularVotosDaSessao] - Fim da Requisição de calcular votos da Sessão!");
        return ResponseEntity.ok().body(votoResponse);
    }

    @GetMapping("/{idSessao}")
    public ResponseEntity<SessaoResponse> findSessaoById(@PathVariable Long idSessao) {
        log.info("[SessaoController*findSessaoById] - Inicio da Requisição de buscar Sessão po ID!");
        Optional<Sessao> sessao = sessaoService.findById(idSessao);
        if (sessao.isPresent()) {
            log.error("[SessaoController*findSessaoById] - Sessão não encontrado!");
            return ResponseEntity.notFound().build();
        }
        SessaoResponse sessaoResponse =  this.sessaoService.toLinks(sessaoMapper.toSessaoResponse(sessao.get()));
        log.info("[SessaoController*findSessaoById] - Fim da Requisição de buscar Sessão po ID!");
        return ResponseEntity.ok().body(sessaoResponse);
    }

    @PostMapping
    public ResponseEntity<SessaoResponse> insertSessao(@Valid @RequestBody SessaoRequest sessaoRequest) {
        log.info("[SessaoController*insertSessao] - Inicio da Requisição de salvar Sessão!");
        sessaoService.insert(sessaoRequest);
        log.info("[SessaoController*insertSessao] - Fim da Requisição de salvar Sessão!");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idSessao}")
    public ResponseEntity<SessaoResponse> updateSessao(@PathVariable Long idSessao, @Valid @RequestBody SessaoRequest sessaoRequest){
        log.info("[SessaoController*updateSessao] - Inicio da Requisição de atualizar Sessão!");
        Optional<Sessao> sessao = sessaoService.findById(idSessao);
        if (sessao.isPresent()) {
            log.error("[SessaoController*updateSessao] - Sessão não encontrado!");
            return ResponseEntity.notFound().build();
        }
        sessaoService.update(sessaoRequest, idSessao);
        log.info("[SessaoController*updateSessao] - Fim da Requisição de atualizar Sessão!");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("${url.sessao.voto}")
    public ResponseEntity<SessaoResponse> voteSessao(@Valid @RequestBody VotoRequest votoRequest) throws ValidationException {
        log.info("[SessaoController*voteSessao] - Inicio da Requisição de atualizar Sessão!");
        Optional<Sessao> sessao = sessaoService.findById(votoRequest.getIdSessao());
        if (!sessao.isPresent()) {
            log.error("[SessaoController*voteSessao] - Sessão não encontrado!");
            return ResponseEntity.notFound().build();
        }

        if(votoRequest.getStatus().equalsIgnoreCase("SIM")
                || votoRequest.getStatus().equalsIgnoreCase("NÃO")
                || votoRequest.getStatus().equalsIgnoreCase("NAO")){
            votoService.vote(votoRequest, sessao.get());
            log.info("[SessaoController*voteSessao] - Fim da Requisição de atualizar Sessão!");
            return ResponseEntity.noContent().build();
        }

        log.error("[SessaoController*voteSessao] - Status do Voto invalido, Vote Sim ou Não!");
        throw new ValidationException("Status do Voto invalido,Vote Sim ou Não!");
    }

    @DeleteMapping("/{idSessao}")
    public ResponseEntity<Void> deleteSessao(@PathVariable Long idSessao){
        log.info("[SessaoController*deleteSessao] - Inicio da Requisição de deletar Associado!");
        Optional<Sessao> sessao = sessaoService.findById(idSessao);
        if (sessao.isPresent()) {
            log.error("[SessaoController*deleteSessao] - Associado não encontrado!");
            return ResponseEntity.notFound().build();
        }
        sessaoService.delete(idSessao);
        log.info("[SessaoController*deleteSessao] - Fim da Requisição de deletar Associado!");
        return ResponseEntity.noContent().build();
    }
}
