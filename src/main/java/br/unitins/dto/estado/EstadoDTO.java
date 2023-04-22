package br.unitins.dto.estado;

import javax.validation.constraints.NotBlank;

public record EstadoDTO(

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome, 
    String sigla

) {
    
}
