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

import br.unitins.dto.LojaDTO;
import br.unitins.dto.LojaResponseDTO;
import br.unitins.model.Loja;
import br.unitins.repository.BrinquedoRepository;
import br.unitins.repository.LojaRepository;

@ApplicationScoped
public class LojaServiceImpl implements LojaService {

    @Inject
    BrinquedoRepository brinquedoRepository;

    @Inject
    LojaRepository lojaRepository;

    @Inject
    Validator validator;

    @Override
    public List<LojaResponseDTO> getAll() {
        List<Loja> list = lojaRepository.listAll();
        return list
                .stream()
                .map(LojaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LojaResponseDTO findById(long id) {
        Loja loja = lojaRepository.findById(id);
        if (loja == null)
            throw new NotFoundException("Loja não encontrada.");
        return new LojaResponseDTO(loja);

    }

    @Override
    @Transactional
    public LojaResponseDTO create(LojaDTO lojadto) {
        Set<ConstraintViolation<LojaDTO>> violations = validator.validate(lojadto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        Loja entity = new Loja();
        entity.setNome(lojadto.getNome());
        lojaRepository.persist(entity);

        return new LojaResponseDTO(entity);
    }

    @Override
    @Transactional
    public LojaResponseDTO update(long id, LojaDTO lojadto) {
        Loja entity = lojaRepository.findById(id);

        entity.setNome(lojadto.getNome());

        return new LojaResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        lojaRepository.deleteById(id);
    }

    @Override
    public List<LojaResponseDTO> findByNome(String nome) {
        List<Loja> list = lojaRepository.findByNome(nome);
        return list
                .stream()
                .map(LojaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return lojaRepository.count();
    }

}
