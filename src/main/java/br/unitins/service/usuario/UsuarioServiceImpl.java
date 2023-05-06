package br.unitins.service.usuario;

import java.util.ArrayList;
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

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.usuario.UsuarioDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.model.Cidade;
import br.unitins.model.Endereco;
import br.unitins.model.Sexo;
import br.unitins.model.Telefone;
import br.unitins.model.Usuario;
import br.unitins.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuariorepository;

    @Inject
    Validator validator;

    @Override
    public List<UsuarioResponseDTO> getAll() {
        List<Usuario> list = usuariorepository.listAll();
        return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(long id) {
        Usuario usuario = usuariorepository.findById(id);
        if (usuario == null)
            throw new NotFoundException("Usuário não encontrado.");
        return new UsuarioResponseDTO(usuario);
    }

    private void validar(UsuarioDTO usuariodto) throws ConstraintViolationException {
        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuariodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO usuariodto) throws ConstraintViolationException {
        validar(usuariodto);

        Usuario entity = new Usuario();
        entity.setNome(usuariodto.nome());
        entity.setCpf(usuariodto.cpf());
        entity.setEmail(usuariodto.email());
        entity.setSenha(usuariodto.senha());
        entity.setSexo(Sexo.valueOf(usuariodto.sexo()));

        entity.setEndereco(new ArrayList<Endereco>());
        for (EnderecoDTO enderecos : usuariodto.endereco()) {
            Endereco endereco = new Endereco();
            endereco.setRua(enderecos.rua());
            endereco.setBairro(enderecos.bairro());
            endereco.setNumero(enderecos.numero());
            endereco.setComplemento(enderecos.complemento());
            endereco.setCep(enderecos.cep());
            endereco.setCidade(new Cidade());
            endereco.getCidade().setId(enderecos.idCidade());
            ;

            entity.getEndereco().add(endereco);
        }

        entity.setTelefone(new ArrayList<Telefone>());
        for (TelefoneDTO telefones : usuariodto.telefone()) {
            Telefone telefone = new Telefone();
            telefone.setCodigoArea(telefones.codigoArea());
            telefone.setNumero(telefones.numero());

            entity.getTelefone().add(telefone);
        }

        usuariorepository.persist(entity);
        return new UsuarioResponseDTO(entity);

    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(long id, UsuarioDTO usuariodto) throws ConstraintViolationException {
        validar(usuariodto);

        Usuario entity = new Usuario();
        entity.setNome(usuariodto.nome());
        entity.setCpf(usuariodto.cpf());
        entity.setEmail(usuariodto.email());
        entity.setSenha(usuariodto.senha());
        entity.setSexo(Sexo.valueOf(usuariodto.sexo()));

        entity.setEndereco(new ArrayList<Endereco>());
        for (EnderecoDTO enderecos : usuariodto.endereco()) {
            Endereco endereco = new Endereco();
            endereco.setRua(enderecos.rua());
            endereco.setBairro(enderecos.bairro());
            endereco.setNumero(enderecos.numero());
            endereco.setComplemento(enderecos.complemento());
            endereco.setCep(enderecos.cep());
            endereco.setCidade(new Cidade());
            endereco.getCidade().setId(enderecos.idCidade());
            ;

            entity.getEndereco().add(endereco);
        }

        entity.setTelefone(new ArrayList<Telefone>());
        for (TelefoneDTO telefones : usuariodto.telefone()) {
            Telefone telefone = new Telefone();
            telefone.setCodigoArea(telefones.codigoArea());
            telefone.setNumero(telefones.numero());

            entity.getTelefone().add(telefone);
        }

        usuariorepository.persist(entity);
        return new UsuarioResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        usuariorepository.deleteById(id);
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        List<Usuario> list = usuariorepository.findByNome(nome);
        return list.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return usuariorepository.count();
    }

}
