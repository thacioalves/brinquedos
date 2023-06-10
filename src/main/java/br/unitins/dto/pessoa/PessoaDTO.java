package br.unitins.dto.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PessoaDTO (

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,

    @Size(max = 14, message = "O cpf deve posssuir no máximo 14 caracteres.")
    String cpf,

    String email,
    Integer sexo
){
    
}
