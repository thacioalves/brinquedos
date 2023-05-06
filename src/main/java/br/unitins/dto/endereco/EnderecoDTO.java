package br.unitins.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
    
        String rua,
        String bairro,
        String numero,

        String complemento,

        @NotBlank(message = "O campo cep deve ser informado.")
        @Size(max = 8, message = "O cep deve posssuir 8 caracteres.")
        String cep,

        Long idCidade

) {

}
