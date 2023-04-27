package br.unitins.dto.usuario;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UsuarioDTO(

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,

        @NotBlank(message = "O campo cpf deve ser informado.")
        @Size(max = 14, message = "O cpf deve posssuir no máximo 14 caracteres.")
        String cpf,

        String email,
        String senha,
        Integer sexo,

        List<Long> idTelefone,
        List<Long> idEndereco,
        List<Long> idProduto

) {

}
