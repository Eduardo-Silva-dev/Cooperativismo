package br.com.sicredi.cooperativismo.controller;

import br.com.sicredi.cooperativismo.controller.dto.request.PautaRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.PautaResponse;
import br.com.sicredi.cooperativismo.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${url.apiPrefix}${url.pauta}")
public class PautaController {

    private final PautaService pautaService;

    @GetMapping
    public ResponseEntity<Page<PautaResponse>> findAllPautas(@PageableDefault(size = 10, page = 0, sort = "titulo") Pageable pageable){
        log.info("[PautaController*findAllPautas] - Inicio da Requisição de buscar todos os Pautas!");
        Page<PautaResponse> listPautaDto = pautaService.findAll(pageable);
        log.info("[PautaController*findAllPautas] - Fim da Requisição de buscar todos os Pautas!");
        return ResponseEntity.ok().body(listPautaDto);
    }

    @GetMapping("/{idPauta}")
    public ResponseEntity<PautaResponse> findPautasById(@PathVariable Long idPauta) {
        log.info("[PautaController*findPautasById] - Inicio da Requisição de buscar Pauta po ID!");
        PautaResponse pautaDto = pautaService.findById(idPauta);
        if (pautaDto == null) {
            log.error("[PautaController*findPautasById] - Pauta não encontrado!");
            return ResponseEntity.notFound().build();
        }
        log.info("[PautaController*findPautasById] - Fim da Requisição de buscar Pauta po ID!");
        return ResponseEntity.ok().body(pautaDto);
    }

    @PostMapping
    public ResponseEntity<PautaResponse> insertPautas(@Valid @RequestBody PautaRequest pautaRequest) {
        log.info("[PautaController*insertPautas] - Inicio da Requisição de salvar Pauta!");
        pautaService.insert(pautaRequest);
        log.info("[PautaController*insertPautas] - Fim da Requisição de salvar Pauta!");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idPauta}")
    public ResponseEntity<PautaResponse> updatePautas(@PathVariable Long idPauta, @Valid @RequestBody PautaRequest pautaRequest){
        log.info("[PautaController*updatePautas] - Inicio da Requisição de atualizar Pauta!");
        PautaResponse pauta = pautaService.findById(idPauta);
        if (pauta == null) {
            log.error("[PautaController*updatePautas] - Pauta não encontrado!");
            return ResponseEntity.notFound().build();
        }
        pautaService.update(pautaRequest, idPauta);
        log.info("[PautaController*updatePautas] - Fim da Requisição de atualizar Pauta!");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idPauta}")
    public ResponseEntity<Void> deletePautas(@PathVariable Long idPauta){
        log.info("[PautaController*deletePautas] - Inicio da Requisição de deletar Pauta!");
        PautaResponse pautaDto = pautaService.findById(idPauta);
        if (pautaDto == null) {
            log.error("[PautaController*deletePautas] - Pauta não encontrado!");
            return ResponseEntity.notFound().build();
        }
        pautaService.delete(idPauta);
        log.info("[PautaController*deletePautas] - Fim da Requisição de deletar Pauta!");
        return ResponseEntity.noContent().build();
    }


}
