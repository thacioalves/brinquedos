package br.unitins.service.cidade;

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

import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.model.Cidade;
import br.unitins.model.Estado;
import br.unitins.repository.CidadeRepository;
import br.unitins.repository.EstadoRepository;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService {

    @Inject
    CidadeRepository cidaderepository;

    EstadoRepository estadorepository;

    @Inject
    Validator validator;

    @Override
    public List<CidadeResponseDTO> getAll() {
        List<Cidade> list = cidaderepository.listAll();
        return list.stream().map(CidadeResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public CidadeResponseDTO findById(Long id) {
        Cidade cidade = cidaderepository.findById(id);
        if (cidade == null)
            throw new NotFoundException("Cidade não encontrada.");
        return new CidadeResponseDTO(cidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO update(Long id, CidadeDTO cidadedto) throws ConstraintViolationException {
        Cidade cidadeUpdate = cidaderepository.findById(id);
        if (cidadeUpdate == null)
            throw new NotFoundException("Cidade não encontrada.");
        validar(cidadedto);

        cidadeUpdate.setNome(cidadedto.nome());

        cidaderepository.persist(cidadeUpdate);
        return new CidadeResponseDTO(cidadeUpdate);
    }

    private void validar(CidadeDTO cidadedto) throws ConstraintViolationException {
        Set<ConstraintViolation<CidadeDTO>> violations = validator.validate(cidadedto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public CidadeResponseDTO create(CidadeDTO cidadedto) throws ConstraintViolationException {
        validar(cidadedto);

        Cidade entity = new Cidade();
        entity.setNome(cidadedto.nome());
        entity.setEstado(new Estado());
        entity.getEstado().setId(cidadedto.idEstado());

        cidaderepository.persist(entity);
        return new CidadeResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cidaderepository.deleteById(id);

    }

    @Override
    public List<CidadeResponseDTO> findByNome(String nome) {
        List<Cidade> list = cidaderepository.findByNome(nome);
        return list.stream().map(CidadeResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return cidaderepository.count();
    }

}
