package br.unitins.dto.brinquedo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
