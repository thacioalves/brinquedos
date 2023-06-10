package br.unitins.service.pagamento;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.dto.pagamento.PagamentoDTO;
import br.unitins.dto.pagamento.PagamentoResponseDTO;
import br.unitins.model.Pagamento;
import br.unitins.repository.PagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService{

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    Validator validator;

    @Override
    public List<PagamentoResponseDTO> getAll() {
        List<Pagamento> list = pagamentoRepository.listAll();
        return list.stream().map(PagamentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public PagamentoResponseDTO findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null)
            throw new NotFoundException("Pagamento não encontrado.");
        return new PagamentoResponseDTO(pagamento);
    }

    private void validar(PagamentoDTO pagamentoDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<PagamentoDTO>> violations = validator.validate(pagamentoDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public PagamentoResponseDTO create(PagamentoDTO pagamentoDTO) throws ConstraintViolationException{
        validar(pagamentoDTO);

        Pagamento entity = new Pagamento();
        entity.setValor(pagamentoDTO.valor());
        entity.setCompra(pagamentoDTO.compra());

        pagamentoRepository.persist(entity);
        return new PagamentoResponseDTO(entity);
    }

    @Override
    @Transactional
    public PagamentoResponseDTO update(Long id, PagamentoDTO pagamentoDTO) throws ConstraintViolationException{
        Pagamento pagamentoUpdate = pagamentoRepository.findById(id);
        if (pagamentoUpdate == null)
            throw new NotFoundException("Pagamento não encontrado.");
        validar(pagamentoDTO);

        pagamentoUpdate.setValor(pagamentoDTO.valor());
        pagamentoUpdate.setCompra(pagamentoDTO.compra());

        pagamentoRepository.persist(pagamentoUpdate);
        return new PagamentoResponseDTO(pagamentoUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pagamentoRepository.deleteById(id);
    }

    @Override
    public List<PagamentoResponseDTO> findByPagamentos(String nome) {
        List<Pagamento> list = pagamentoRepository.findByPagamentos(nome);
        return list.stream().map(PagamentoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return pagamentoRepository.count();
    }
    
}
