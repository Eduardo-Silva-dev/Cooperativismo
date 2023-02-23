package br.com.sicredi.cooperativismo.service.impl;

import br.com.sicredi.cooperativismo.controller.AssociadoController;
import br.com.sicredi.cooperativismo.controller.dto.request.AssociadoRequest;
import br.com.sicredi.cooperativismo.controller.dto.response.AssociadoResponse;
import br.com.sicredi.cooperativismo.controller.exception.NotFoundExeption;
import br.com.sicredi.cooperativismo.entity.Associado;
import br.com.sicredi.cooperativismo.entity.mapper.AssociadoMapper;
import br.com.sicredi.cooperativismo.repository.AssociadoRepository;
import br.com.sicredi.cooperativismo.service.AssociadoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@RequiredArgsConstructor
public class AssociadoServiceImpl implements AssociadoService {

    Logger log = LogManager.getLogger(AssociadoServiceImpl.class);

    private final AssociadoRepository associadoRepository;

    private final AssociadoMapper associadoMapper;

    @Override
    public Optional<Associado> findById (long id){
        log.info("[AssociadoServiceImpl*findById] - Inicio do Processo de Buscar Associado por ID!");
        Optional<Associado> associado = associadoRepository.findById(id);
        if(!associado.isPresent()){
            log.error("[VotoServiceImpl*findById] - Associado não encontrada!");
            throw new NotFoundExeption("Associado não encontrada! Id: " + id + ", Tipo: " + Associado.class.getName());
        }
        log.info("[AssociadoServiceImpl*findById] - Buscar Associado por ID com sucesso!");
        log.info("[AssociadoServiceImpl*findById] - Fim do Processo de Buscar Associado por ID!");
        return associado;
    }

    @Override
    public Page<AssociadoResponse> findAll(Pageable pageable){
        try {
            log.info("[AssociadoServiceImpl*findAll] - Inicio do Processo de Buscar todos os Associados!");
            Page<Associado> listAssociado = associadoRepository.findAll(pageable);
            List<AssociadoResponse> listAssociadoResponse = listAssociado.stream()
                    .map(associado -> associadoMapper.toAssociadoResponse(associado))
                    .map(associado -> toLinks(associado))
                    .collect(Collectors.toList());
            log.info("[AssociadoServiceImpl*findAll] - Buscar todos os Associados com sucesso!");
            log.info("[AssociadoServiceImpl*findAll] - Fim do Processo de Buscar todos os Associados!");
            return new PageImpl<>(listAssociadoResponse);
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*findById] - Erro no Processo de buscar todos os associados! - {}", e.getMessage());
            throw new RuntimeException("[AssociadoServiceImpl*findById] - Erro no Processo de buscar todos os associados! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AssociadoResponse insert(AssociadoRequest associadoRequest){
        try {
            log.info("[AssociadoServiceImpl*insert] - Inicio do Processo de salvar Associado!");
            Associado associado = associadoMapper.toAssociado(associadoRequest);
            log.info("[AssociadoServiceImpl*insert] - Associado salvo com sucesso!");
            log.info("[AssociadoServiceImpl*insert] - Fim do Processo de salvar Associado!");
            return this.toLinks(
                    associadoMapper.toAssociadoResponse(
                            associadoRepository.save(associado)
                    ));
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*insert] - Erro no Processo de salvar associado! - {}", e.getMessage());
            throw new RuntimeException("[AssociadoServiceImpl*insert] - Erro no Processo de salvar associado! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public AssociadoResponse update(AssociadoRequest associadoRequest, Long idAssociado){
        try {
            log.info("[AssociadoServiceImpl*update] - Inicio do Processo de update Associado!");
            Associado associado = associadoMapper.toAssociado(associadoRequest);
            associado.setId(idAssociado);
            log.info("[AssociadoServiceImpl*update] - Associado atualizado com sucesso!");
            log.info("[AssociadoServiceImpl*update] - Fim do Processo de update Associado!");
            return this.toLinks(
                    associadoMapper.toAssociadoResponse(
                            associadoRepository.save(associado)
                    ));
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*update] - Erro no Processo de atualizar associado! - {}", e.getMessage());
            throw new RuntimeException("[AssociadoServiceImpl*update] - Erro no Processo de atualizar associado! - " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(long id){
            log.info("[AssociadoServiceImpl*delete] - Inicio do Processo de delete Associado!");
            findById(id);
        try {
            associadoRepository.deleteById(id);
            log.info("[AssociadoServiceImpl*delete] - Associado deletado com sucesso!");
            log.info("[AssociadoServiceImpl*delete] - Fim do Processo de delete Associado!");
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*delete] - Erro no Processo de deletar associado! - {}", e.getMessage());
            throw new RuntimeException("[AssociadoServiceImpl*delete] - Erro no Processo de deletar associado! - " + e.getMessage());
        }
    }

    public Optional<Associado> findByCpf(String cpf) {
        try {
            log.info("[AssociadoServiceImpl*findByCpf] - Inicio do Processo de Busca de Associado por CPF!");
            Optional<Associado> associado = associadoRepository.findByCpf(cpf);
            log.info("[AssociadoServiceImpl*findByCpf] - Busca de Associado por CPF com sucesso!");
            log.info("[AssociadoServiceImpl*findByCpf] - Fim do Processo de Busca de Associado por CPF!");
            return associado;
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*findByCpf] - Erro no Processo de buscar associado por CPF! - {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public AssociadoResponse toLinks(AssociadoResponse associadoResponse){
        try {
            log.info("[AssociadoServiceImpl*toLinks] - Inicio do Processo de Links Hateoas!");

            Pageable pageable = (Pageable) PageRequest.of(0, 10);

            associadoResponse.add(linkTo(methodOn(AssociadoController.class)
                    .findAssociadoById(associadoResponse.getId())).withSelfRel());

            associadoResponse.add(linkTo(methodOn(AssociadoController.class)
                    .findAllAssociado(pageable)).withRel("Lista de Associados"));

            associadoResponse.add(linkTo(methodOn(AssociadoController.class)
                    .updateAssociado(associadoResponse.getId(), new AssociadoRequest())).withRel("Update"));

            associadoResponse.add(linkTo(methodOn(AssociadoController.class)
                    .deleteAssociado(associadoResponse.getId())).withRel("Delete"));

            log.info("[AssociadoServiceImpl*toLinks] - Fim do Processo de Links Hateoas!");
            return associadoResponse;
        } catch (Exception e) {
            log.error("[AssociadoServiceImpl*toLinks] - Erro no Processo de criar links Associado! - {}", e.getMessage());
            throw new RuntimeException("[AssociadoServiceImpl*toLinks] - Erro no Processo de criar links Associado! - " + e.getMessage());
        }
    }

}
