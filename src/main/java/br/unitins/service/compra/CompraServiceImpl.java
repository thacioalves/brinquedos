package br.unitins.service.compra;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.dto.compra.CompraDTO;
import br.unitins.dto.compra.CompraResponseDTO;
import br.unitins.model.Compra;
import br.unitins.repository.CompraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {

    @Inject
    CompraRepository compraRepository;

    @Inject
    Validator validator;

    @Override
    public List<CompraResponseDTO> getAll() {
        List<Compra> list = compraRepository.listAll();
        return list.stream().map(CompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        Compra compra = compraRepository.findById(id);
        if (compra == null)
            return null;
        return new CompraResponseDTO(compra);
    }

    private void validar(CompraDTO compradto) throws ConstraintViolationException {
        Set<ConstraintViolation<CompraDTO>> violations = validator.validate(compradto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public CompraResponseDTO create(CompraDTO compradto) throws ConstraintViolationException {
        validar(compradto);

        Compra entity = new Compra();
        entity.setData(compradto.data());
        entity.setTotalCompra(compradto.totalCompra());

        compraRepository.persist(entity);
        return new CompraResponseDTO(entity);
    }

    @Override
    @Transactional
    public CompraResponseDTO update(Long id, CompraDTO compradto) {
        Compra compraUpdate = compraRepository.findById(id);
        if (compraUpdate == null)
            throw new NotFoundException("Compra não encontrada.");
        validar(compradto);

        compraUpdate.setData(compradto.data());
        compraUpdate.setTotalCompra(compradto.totalCompra());

        compraRepository.persist(compraUpdate);
        return new CompraResponseDTO(compraUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        compraRepository.deleteById(id);
    }

    @Override
    public List<CompraResponseDTO> findByNome(String nome) {
        List<Compra> list = compraRepository.findByNome(nome);
        return list.stream().map(CompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return compraRepository.count();
    }

}
