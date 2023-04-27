package br.unitins.service.endereco;

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

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.endereco.EnderecoResponseDTO;
import br.unitins.model.Cidade;
import br.unitins.model.Endereco;
import br.unitins.repository.EnderecoRepository;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecorepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoResponseDTO> getAll() {
        List<Endereco> list = enderecorepository.listAll();
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecorepository.findById(id);
        if (endereco == null)
            throw new NotFoundException("Endereço não encontrado.");
        return new EnderecoResponseDTO(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO enderecodto) throws ConstraintViolationException {
        validar(enderecodto);

        Endereco entity = new Endereco();
        entity.setRua(enderecodto.rua());
        entity.setBairro(enderecodto.bairro());
        entity.setNumero(enderecodto.numero());
        entity.setComplemento(enderecodto.complemento());
        entity.setCep(enderecodto.cep());
        entity.setCidade(new Cidade());
        entity.getCidade().setId(enderecodto.idCidade());
    
        enderecorepository.persist(entity);
        return new EnderecoResponseDTO(entity);
    }

    private void validar(EnderecoDTO enderecodto) throws ConstraintViolationException {
        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoDTO enderecodto) throws ConstraintViolationException {
        Endereco enderecoUpdate = enderecorepository.findById(id);
        if (enderecoUpdate == null)
            throw new NotFoundException("Endereco não encontrado.");
        validar(enderecodto);

        enderecoUpdate.setRua(enderecodto.rua());
        enderecoUpdate.setBairro(enderecodto.bairro());
        enderecoUpdate.setNumero(enderecodto.numero());
        enderecoUpdate.setComplemento(enderecodto.complemento());
        enderecoUpdate.setCep(enderecodto.cep());

        enderecorepository.persist(enderecoUpdate);
        return new EnderecoResponseDTO(enderecoUpdate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        enderecorepository.deleteById(id);
    }

    @Override
    public List<EnderecoResponseDTO> findByNome(String nome) {
        List<Endereco> list = enderecorepository.findByNome(nome);
        return list.stream().map(EnderecoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return enderecorepository.count();
    }

}
