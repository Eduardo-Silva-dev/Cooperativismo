package br.com.sicredi.cooperativismo.service.impl;

import br.com.sicredi.cooperativismo.controller.PautaController;
import br.com.sicredi.cooperativismo.controller.dto.request.PautaRequest;
import br.com.sicredi.cooperativismo.controller.exception.NotFoundExeption;
import br.com.sicredi.cooperativismo.entity.Pauta;
import br.com.sicredi.cooperativismo.controller.dto.response.PautaResponse;
import br.com.sicredi.cooperativismo.entity.mapper.PautaMapper;
import br.com.sicredi.cooperativismo.repository.PautaRepository;
import br.com.sicredi.cooperativismo.service.PautaService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@RequiredArgsConstructor
public class PautaServiceImpl implements PautaService {

    Logger log = LogManager.getLogger(PautaServiceImpl.class);

    private final PautaRepository pautaRepository;

    private final PautaMapper pautaMapper;

    @Override
    public PautaResponse findById (long id){
        log.info("[PautaServiceImpl*findById] - Inicio do Processo de Buscar Pauta por ID!");
        Optional<Pauta> pauta = pautaRepository.findById(id);
        if(!pauta.isPresent()){
            log.error("[VotoServiceImpl*findById] - Pauta não encontrada!");
            throw new NotFoundExeption("Pauta não encontrada! Id: " + id + ", Tipo: " + Pauta.class.getName());
        }
        log.info("[PautaServiceImpl*findById] - Buscar Pauta por ID com sucesso!");
        log.info("[PautaServiceImpl*findById] - Fim do Processo de Buscar Pauta por ID!");
        return this.toLinks(pautaMapper.toPautaResponse(pauta.get()));
    }

    @Override
    public Page<PautaResponse> findAll(Pageable pageable){
        try {
            log.info("[PautaServiceImpl*findAll] - Inicio do Processo de Buscar todos os Pautas!");
            Page<Pauta> listPauta = pautaRepository.findAll(pageable);
            List<PautaResponse> listPautaResponse = listPauta.stream()
                                                        .map(pauta -> pautaMapper.toPautaResponse(pauta))
                                                        .map(pauta -> toLinks(pauta))
                                                        .collect(Collectors.toList());
            log.info("[PautaServiceImpl*findAll] - Buscar todos os Pautas com sucesso!");
            log.info("[PautaServiceImpl*findAll] - Fim do Processo de Buscar todos os Pautas!");
            return new PageImpl<>(listPautaResponse);
        } catch (Exception e) {
            log.error("[PautaServiceImpl*findAll] - Erro no Processo de buscar todas as Pautas! - {}", e.getMessage());
            throw new RuntimeException("[PautaServiceImpl*findAll] - Erro no Processo de buscar todas as Pautas! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void insert(PautaRequest pautaRequest){
        try {
            log.info("[PautaServiceImpl*insert] - Inicio do Processo de salvar Pauta!");
            pautaRepository.save(pautaMapper.toPauta(pautaRequest));
            log.info("[PautaServiceImpl*insert] - Pauta salvo com sucesso!");
            log.info("[PautaServiceImpl*insert] - Fim do Processo de salvar Pauta!");
        } catch (Exception e) {
            log.error("[PautaServiceImpl*insert] - Erro no Processo de salvar Pauta! - {}", e.getMessage());
            throw new RuntimeException("[PautaServiceImpl*insert] - Erro no Processo de salvar Pauta! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(PautaRequest pautaRequest, Long idPauta){
            log.info("[PautaServiceImpl*update] - Inicio do Processo de update Pauta!");
            Pauta pauta = pautaMapper.toPauta(pautaRequest);
            pauta.setId(idPauta);
        try {
            pautaRepository.save(pauta);
            log.info("[PautaServiceImpl*update] - Pauta atualizado com sucesso!");
            log.info("[PautaServiceImpl*update] - Fim do Processo de update Pauta!");
        } catch (Exception e) {
            log.error("[PautaServiceImpl*update] - Erro no Processo de atualizar Pauta! - {}", e.getMessage());
            throw new RuntimeException("[PautaServiceImpl*update] - Erro no Processo de atualizar Pauta! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(long id){
            log.info("[PautaServiceImpl*delete] - Inicio do Processo de delete Pauta!");
            findById(id);
        try {
            pautaRepository.deleteById(id);
            log.info("[PautaServiceImpl*delete] - Pauta deletado com sucesso!");
            log.info("[PautaServiceImpl*delete] - Fim do Processo de delete Pauta!");
        } catch (Exception e) {
            log.error("[PautaServiceImpl*delete] - Erro no Processo de deletar Pauta! - {}", e.getMessage());
            throw new RuntimeException("[PautaServiceImpl*delete] - Erro no Processo de deletar Pauta! - " + e.getMessage());
        }
    }

    private PautaResponse toLinks(PautaResponse pautaDto){
        try {
            log.info("[PautaServiceImpl*toLinks] - Inicio do Processo de Links Hateoas!");

            Pageable pageable = PageRequest.of(0, 10);

            pautaDto.add(linkTo(methodOn(PautaController.class)
                    .findPautasById(pautaDto.getId())).withSelfRel());

            pautaDto.add(linkTo(methodOn(PautaController.class)
                    .findAllPautas(pageable)).withRel("Lista de Pauta"));

            pautaDto.add(linkTo(methodOn(PautaController.class)
                    .updatePautas(pautaDto.getId(), new PautaRequest())).withRel("Update"));

            pautaDto.add(linkTo(methodOn(PautaController.class)
                    .deletePautas(pautaDto.getId())).withRel("Delete"));

            log.info("[PautaServiceImpl*toLinks] - Fim do Processo de Links Hateoas!");
            return pautaDto;
        } catch (Exception e) {
            log.error("[PautaServiceImpl*toLinks] - Erro no Processo de criar links Pauta! - {}", e.getMessage());
            throw new RuntimeException("[PautaServiceImpl*toLinks] - Erro no Processo de criar links Pauta! - " + e.getMessage());
        }
    }
}
