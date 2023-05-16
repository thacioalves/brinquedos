package br.unitins.dto.endereco;

import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.model.Endereco;

public record EnderecoResponseDTO(

        Long id,
        String rua,
        String bairro,
        String numero,
        String complemento,
        String cep,
        CidadeResponseDTO cidade

) {
    public EnderecoResponseDTO(Endereco endereco){
        this(endereco.getId(), endereco.getRua(), endereco.getBairro(), endereco.getNumero(),
        endereco.getComplemento(), endereco.getCep(), new CidadeResponseDTO(endereco.getCidade())
        );
    }
}
