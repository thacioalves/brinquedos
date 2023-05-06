package br.unitins.dto.brinquedo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BrinquedoDTO(

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,
        
        Double preco,

        @Size(max = 2, message = "O campo idade deve conter 2 caracteres.")
        String idade,

        @NotNull(message = "O campo marca não pode ser nulo.") 
        String marca,

        Integer estoque,

        String descricao

) {

}
