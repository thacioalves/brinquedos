package br.unitins.dto.estado;

import jakarta.validation.constraints.NotBlank;

public record EstadoDTO(

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome, 
    String sigla

) {
    
}
