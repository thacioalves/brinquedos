package br.unitins.dto.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record EnderecoDTO(
    
        String logradouro,
        String bairro,

        @Size(max = 2, message = "O numero deve posssuir 2 caracteres.")
        String numero,

        String complemento,

        @NotBlank(message = "O campo cep deve ser informado.")
        @Size(max = 8, message = "O cep deve posssuir 8 caracteres.")
        String cep,

        Long idCidade

) {

}
