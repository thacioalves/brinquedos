package br.unitins.service.telefone;

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

import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;
import br.unitins.model.Telefone;
import br.unitins.repository.TelefoneRepository;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    TelefoneRepository telefonerepository;

    @Inject
    Validator validator;

    @Override
    public List<TelefoneResponseDTO> getAll() {
        List<Telefone> list = telefonerepository.listAll();
        return list.stream().map(TelefoneResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public TelefoneResponseDTO findById(long id) {
        Telefone telefone = telefonerepository.findById(id);
        if (telefone == null)
            throw new NotFoundException("Telefone não encontrado.");
        return new TelefoneResponseDTO(telefone);
    }

    private void validar(TelefoneDTO telefonedto) throws ConstraintViolationException {
        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefonedto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneDTO telefonedto) throws ConstraintViolationException {
        validar(telefonedto);

        Telefone entity = new Telefone();
        entity.setCodigoArea(telefonedto.codigoArea());
        entity.setNumero(telefonedto.numero());

        telefonerepository.persist(entity);
        return new TelefoneResponseDTO(entity);
    }

    @Override
    @Transactional
    public TelefoneResponseDTO update(long id, TelefoneDTO telefonedto) throws ConstraintViolationException {
        Telefone telefoneUpdate = telefonerepository.findById(id);
        if (telefoneUpdate == null)
            throw new NotFoundException("Telefone não encontrado.");
        validar(telefonedto);

        telefoneUpdate.setCodigoArea(telefonedto.codigoArea());
        telefoneUpdate.setNumero(telefonedto.numero());

        telefonerepository.persist(telefoneUpdate);
        return new TelefoneResponseDTO(telefoneUpdate);

    }

    @Override
    @Transactional
    public void delete(long id) {
        telefonerepository.deleteById(id);
    }

    @Override
    public List<TelefoneResponseDTO> findByNumero(String numero) {
        List<Telefone> list = telefonerepository.findByNumero(numero);
        return list.stream().map(TelefoneResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return telefonerepository.count();
    }

}
