package br.unitins.dto.usuario;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.telefone.TelefoneDTO;

public record UsuarioDTO(

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,

        @NotBlank(message = "O campo cpf deve ser informado.")
        @Size(max = 14, message = "O cpf deve posssuir no máximo 14 caracteres.")
        String cpf,

        String email,
        String senha,
        Integer sexo,

        List<TelefoneDTO> telefone,
        List<EnderecoDTO> endereco

) {

}
