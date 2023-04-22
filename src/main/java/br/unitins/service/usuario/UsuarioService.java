package br.unitins.service.usuario;

import java.util.List;

import br.unitins.dto.usuario.UsuarioDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;

public interface UsuarioService {
    List<UsuarioResponseDTO> getAll();

    UsuarioResponseDTO findById(long id);

    UsuarioResponseDTO create(UsuarioDTO usuariodto);

    UsuarioResponseDTO update(long id, UsuarioDTO usuariodto);

    void delete(long id);

    List<UsuarioResponseDTO> findByNome(String nome);

    long count();
}
