package br.unitins.service.brinquedo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.dto.brinquedo.BrinquedoDTO;
import br.unitins.dto.brinquedo.BrinquedoResponseDTO;
import br.unitins.model.Brinquedo;
import br.unitins.repository.BrinquedoRepository;

@ApplicationScoped
public class BrinquedoServiceImpl implements BrinquedoService {

    @Inject
    BrinquedoRepository brinquedorepository;

    @Inject
    Validator validator;

    @Override
    public List<BrinquedoResponseDTO> getAll() {
        List<Brinquedo> list = brinquedorepository.listAll();
        return list.stream().map(BrinquedoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public BrinquedoResponseDTO findById(long id) {
        Brinquedo brinquedo = brinquedorepository.findById(id);
        if (brinquedo == null)
            throw new NotFoundException("Brinquedo não encontrado.");
        return new BrinquedoResponseDTO(brinquedo);

    }

    private void validar(BrinquedoDTO brinquedodto) throws ConstraintViolationException {
        Set<ConstraintViolation<BrinquedoDTO>> violations = validator.validate(brinquedodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public BrinquedoResponseDTO create(BrinquedoDTO brinquedodto) throws ConstraintViolationException {
        validar(brinquedodto);

        Brinquedo entity = new Brinquedo();
        entity.setNome(brinquedodto.nome());
        entity.setIdade(brinquedodto.idade());
        entity.setPreco(brinquedodto.preco());
        entity.setMarca(brinquedodto.marca());
        entity.setEstoque(brinquedodto.estoque());
        entity.setDescricao(brinquedodto.descricao());

        brinquedorepository.persist(entity);
        return new BrinquedoResponseDTO(entity);
    }

    @Override
    @Transactional
    public BrinquedoResponseDTO update(long id, BrinquedoDTO brinquedodto) throws ConstraintViolationException {
        Brinquedo brinquedoUpdate = brinquedorepository.findById(id);
        if (brinquedoUpdate == null)
            throw new NotFoundException("Brinquedo não encontrado.");
        validar(brinquedodto);

        brinquedoUpdate.setNome(brinquedodto.nome());
        brinquedoUpdate.setIdade(brinquedodto.idade());
        brinquedoUpdate.setPreco(brinquedodto.preco());
        brinquedoUpdate.setMarca(brinquedodto.marca());
        brinquedoUpdate.setEstoque(brinquedodto.estoque());
        brinquedoUpdate.setDescricao(brinquedodto.descricao());

        brinquedorepository.persist(brinquedoUpdate);
        return new BrinquedoResponseDTO(brinquedoUpdate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        brinquedorepository.deleteById(id);
    }

    @Override
    public List<BrinquedoResponseDTO> findByNome(String nome) {
        List<Brinquedo> list = brinquedorepository.findByNome(nome);
        return list.stream().map(BrinquedoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return brinquedorepository.count();
    }

}
