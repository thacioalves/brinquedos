package br.unitins.dto.cidade;

import jakarta.validation.constraints.NotBlank;

import br.unitins.dto.estado.EstadoResponseDTO;
import br.unitins.model.Cidade;

public record CidadeResponseDTO(

        Long id,

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,
        
        EstadoResponseDTO estado

){
    public CidadeResponseDTO(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), new EstadoResponseDTO(cidade.getEstado()));
    }
}
