package br.unitins.service.estado;

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

import br.unitins.dto.estado.EstadoDTO;
import br.unitins.dto.estado.EstadoResponseDTO;
import br.unitins.model.Estado;
import br.unitins.repository.EstadoRepository;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository estadorepository;

    @Inject
    Validator validator;

    @Override
    public List<EstadoResponseDTO> getAll() {
        List<Estado> list = estadorepository.listAll();
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EstadoResponseDTO findById(Long id) {
        Estado estado = estadorepository.findById(id);
        if (estado == null)
            throw new NotFoundException("Estado não encontrado.");
        return new EstadoResponseDTO(estado);
    }

    private void validar(EstadoDTO estadodto) throws ConstraintViolationException {
        Set<ConstraintViolation<EstadoDTO>> violations = validator.validate(estadodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public EstadoResponseDTO create(EstadoDTO estadodto) throws ConstraintViolationException {
        validar(estadodto);

        Estado entity = new Estado();
        entity.setNome(estadodto.nome());
        entity.setSigla(estadodto.sigla());

        estadorepository.persist(entity);
        return new EstadoResponseDTO(entity);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(Long id, EstadoDTO estadodto) throws ConstraintViolationException {
        Estado estadoUpdate = estadorepository.findById(id);
        if (estadoUpdate == null)
            throw new NotFoundException("Estado não encontrado.");
        validar(estadodto);

        estadoUpdate.setNome(estadodto.nome());
        estadoUpdate.setSigla(estadodto.sigla());

        estadorepository.persist(estadoUpdate);
        return new EstadoResponseDTO(estadoUpdate);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        estadorepository.deleteById(id);
    }

    @Override
    public List<EstadoResponseDTO> findByNome(String nome) {
        List<Estado> list = estadorepository.findByNome(nome);
        return list.stream().map(EstadoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return estadorepository.count();
    }

}
