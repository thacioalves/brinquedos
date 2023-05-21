package br.unitins.service.usuario;

import java.util.List;

import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.model.Usuario;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(long id);

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO findByLogin(String login);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    // UsuarioResponseDTO create(UsuarioDTO usuariodto);

    // UsuarioResponseDTO update(long id, UsuarioDTO usuariodto);

    void delete(long id);

    List<UsuarioResponseDTO> findByNome(String nome);

    long count();
}
