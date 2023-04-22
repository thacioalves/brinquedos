package br.unitins.dto.telefone;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.unitins.model.Telefone;

public record TelefoneResponseDTO(

        Long id,

        @NotBlank(message = "O DDD deve ser informado.")
        @Size(max = 3, message = "O cep deve posssuir 3 caracteres.")
        String codigoArea,

        @NotBlank(message = "O campo numero deve ser informado.")
        @Size(max = 9, message = "O cep deve posssuir 9 caracteres.")
        String numero

) {
    public TelefoneResponseDTO(Telefone telefone){
        this(telefone.getId(), telefone.getCodigoArea(), telefone.getNumero());
    }
}
