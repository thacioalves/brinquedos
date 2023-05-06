package br.unitins.dto.telefone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneDTO(

        @NotBlank(message = "O DDD deve ser informado.")
        @Size(max = 3, message = "O cep deve posssuir 3 caracteres.")
        String codigoArea,

        @NotBlank(message = "O campo numero deve ser informado.")
        @Size(max = 9, message = "O cep deve posssuir 9 caracteres.")
        String numero
        
) {

}
