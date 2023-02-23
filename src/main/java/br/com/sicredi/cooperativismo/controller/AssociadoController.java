package br.com.sicredi.cooperativismo.controller;

import br.com.sicredi.cooperativismo.controller.dto.request.AssociadoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.AssociadoResponse;
import br.com.sicredi.cooperativismo.entity.Associado;
import br.com.sicredi.cooperativismo.entity.mapper.AssociadoMapper;
import br.com.sicredi.cooperativismo.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${url.apiPrefix}${url.associado}")
public class AssociadoController {

    private final AssociadoService associadoService;

    private final AssociadoMapper associadoMapper;

    @GetMapping
    public ResponseEntity<Page<AssociadoResponse>> findAllAssociado(@PageableDefault(size = 10, page = 0, sort = "cpf") Pageable pageable){
        log.info("[AssociadoController*findAllAssociado] - Inicio da Requisição de buscar todos os Associados!");
        Page<AssociadoResponse> listAssociadoResponse = associadoService.findAll(pageable);
        log.info("[AssociadoController*findAllAssociado] - Fim da Requisição de buscar todos os Associados!");
        return ResponseEntity.ok().body(listAssociadoResponse);
    }

    @GetMapping("/{idAssociado}")
    public ResponseEntity<AssociadoResponse> findAssociadoById(@PathVariable Long idAssociado) {
        log.info("[AssociadoController*findAssociadoById] - Inicio da Requisição de buscar Associado po ID!");
        Optional<Associado> associado = associadoService.findById(idAssociado);
        if (associado.isPresent()) {
            log.error("[AssociadoController*findAssociadoById] - Associado não encontrado!");
            return ResponseEntity.notFound().build();
        }
        AssociadoResponse associadoResponse = this.associadoService.toLinks(associadoMapper.toAssociadoResponse(associado.get()));
        log.info("[AssociadoController*findAssociadoById] - Fim da Requisição de buscar Associado po ID!");
        return ResponseEntity.ok().body(associadoResponse);
    }

    @PostMapping
    public ResponseEntity<AssociadoResponse> insertAssociado(@Valid @RequestBody AssociadoRequest associadoRequest) {
        log.info("[AssociadoController*insertAssociado] - Inicio da Requisição de salvar Associado!");
        associadoService.insert(associadoRequest);
        log.info("[AssociadoController*insertAssociado] - Fim da Requisição de salvar Associado!");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idAssociado}")
    public ResponseEntity<AssociadoResponse> updateAssociado(@PathVariable Long idAssociado, @Valid @RequestBody AssociadoRequest associadoRequest){
        log.info("[AssociadoController*updateAssociado] - Inicio da Requisição de atualizar Associado!");
        Optional<Associado> associado = associadoService.findById(idAssociado);
        if (associado.isPresent()) {
            log.error("[AssociadoController*updateAssociado] - Associado não encontrado!");
            return ResponseEntity.notFound().build();
        }
        associadoService.update(associadoRequest, idAssociado);
        log.info("[AssociadoController*updateAssociado] - Fim da Requisição de atualizar Associado!");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAssociado}")
    public ResponseEntity<Void> deleteAssociado(@PathVariable Long idAssociado){
        log.info("[AssociadoController*deleteAssociado] - Inicio da Requisição de deletar Associado!");
        Optional<Associado> associado = associadoService.findById(idAssociado);
        if (associado.isPresent()) {
            log.error("[AssociadoController*deleteAssociado] - Associado não encontrado!");
            return ResponseEntity.notFound().build();
        }
        associadoService.delete(idAssociado);
        log.info("[AssociadoController*deleteAssociado] - Fim da Requisição de deletar Associado!");
        return ResponseEntity.noContent().build();
    }

}