package br.unitins.dto.endereco;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.model.Endereco;

public record EnderecoResponseDTO(

        Long id,
        String rua,
        String bairro,

        String numero,

        String complemento,

        @NotBlank(message = "O campo cep deve ser informado.")
        @Size(max = 8, message = "O cep deve posssuir 8 caracteres.")
        String cep,
        CidadeResponseDTO cidade

) {
    public EnderecoResponseDTO(Endereco endereco){
        this(endereco.getId(), endereco.getRua(), endereco.getBairro(), endereco.getNumero(),
        endereco.getComplemento(), endereco.getCep(), new CidadeResponseDTO(endereco.getCidade())
        );
    }
}
