package br.unitins.dto.estado;

import javax.validation.constraints.NotBlank;

import br.unitins.model.Estado;

public record EstadoResponseDTO(

    Long id,
    
    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,
    
    String sigla

) {
    public EstadoResponseDTO(Estado estado){
        this(estado.getId(), estado.getNome(), estado.getSigla());
    }
}
