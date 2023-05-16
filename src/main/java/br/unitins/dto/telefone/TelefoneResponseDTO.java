package br.unitins.dto.telefone;

import br.unitins.model.Telefone;

public record TelefoneResponseDTO(

        Long id,
        String codigoArea,
        String numero

) {
    public TelefoneResponseDTO(Telefone telefone){
        this(telefone.getId(), telefone.getCodigoArea(), telefone.getNumero());
    }
}
