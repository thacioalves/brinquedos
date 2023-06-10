package br.unitins.dto.pessoa;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.unitins.model.Pessoa;
import br.unitins.model.Sexo;

public record PessoaResponseDTO(

    Long id,
    String nome,
    String cpf,
    String email,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Sexo sexo
) {
    public PessoaResponseDTO(Pessoa pessoa){
        this(pessoa.getId(), pessoa.getNome(), pessoa.getCpf(), pessoa.getEmail(), pessoa.getSexo());
    }
}
