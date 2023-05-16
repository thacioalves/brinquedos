package br.unitins.dto.usuario;

import java.util.List;

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

        String login,
        String senha,

        List<TelefoneDTO> telefone,
        List<EnderecoDTO> endereco

) {

}
