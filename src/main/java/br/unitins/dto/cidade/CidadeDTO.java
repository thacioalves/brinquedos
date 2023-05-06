package br.unitins.dto.cidade;

import jakarta.validation.constraints.NotBlank;

public record CidadeDTO (

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,
    
    Long idEstado

){
    
}
