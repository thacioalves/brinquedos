package br.unitins.dto.cidade;

import javax.validation.constraints.NotBlank;

import br.unitins.model.Cidade;
import br.unitins.model.Estado;

public record CidadeResponseDTO(

        Long id,

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,
        
        Estado estado

){
    public CidadeResponseDTO(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), cidade.getEstado());
    }
}
