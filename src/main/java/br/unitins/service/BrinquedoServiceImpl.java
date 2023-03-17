package br.unitins.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import br.unitins.dto.BrinquedoDTO;
import br.unitins.dto.BrinquedoResponseDTO;
import br.unitins.model.Brinquedo;
import br.unitins.repository.BrinquedoRepository;
import br.unitins.repository.LojaRepository;

@ApplicationScoped
public class BrinquedoServiceImpl implements BrinquedoService {

    @Inject
    BrinquedoRepository brinquedoRepository;

    @Inject
    LojaRepository lojaRepository;

    @Inject
    Validator validator;

    @Override
    public List<BrinquedoResponseDTO> getAll() {
        List<Brinquedo> list = brinquedoRepository.listAll();
        return list
                .stream()
                .map(BrinquedoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public BrinquedoResponseDTO findById(long id) {
        Brinquedo brinquedo = brinquedoRepository.findById(id);
        if (brinquedo == null)
            throw new NotFoundException("Brinquedo não encontrado.");
        return new BrinquedoResponseDTO(brinquedo);

    }

    @Override
    @Transactional
    public BrinquedoResponseDTO create(BrinquedoDTO brinquedodto) {
        Set<ConstraintViolation<BrinquedoDTO>> violations = validator.validate(brinquedodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        Brinquedo entity = new Brinquedo();
        entity.setNome(brinquedodto.getNome());
        entity.setMarca(brinquedodto.getMarca());
        entity.setLoja(lojaRepository.findById(brinquedodto.getIdLoja()));
        brinquedoRepository.persist(entity);

        return new BrinquedoResponseDTO(entity);
    }

    @Override
    @Transactional
    public BrinquedoResponseDTO update(long id, BrinquedoDTO brinquedodto) {
        Brinquedo entity = brinquedoRepository.findById(id);

        entity.setNome(brinquedodto.getNome());
        entity.setMarca(brinquedodto.getMarca());
        entity.setLoja(lojaRepository.findById(brinquedodto.getIdLoja()));

        return new BrinquedoResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        brinquedoRepository.deleteById(id);
    }

    @Override
    public List<BrinquedoResponseDTO> findByNome(String nome) {
        List<Brinquedo> list = brinquedoRepository.findByNome(nome);
        return list
                .stream()
                .map(BrinquedoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return brinquedoRepository.count();
    }

}
