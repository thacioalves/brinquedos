package br.unitins.dto.cidade;

import br.unitins.dto.estado.EstadoResponseDTO;
import br.unitins.model.Cidade;

public record CidadeResponseDTO(

        Long id,
        String nome,
        EstadoResponseDTO estado

){
    public CidadeResponseDTO(Cidade cidade) {
        this(cidade.getId(), cidade.getNome(), new EstadoResponseDTO(cidade.getEstado()));
    }
}
