package br.unitins.dto.brinquedo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import br.unitins.model.Brinquedo;

public record BrinquedoResponseDTO(

        Long id,

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,

        @NotNull(message = "O campo marca não pode ser nulo.") 
        String marca,

        @Size(max = 2, message = "O campo idade deve conter 2 caracteres.")
        String idade,

        Double preco,
        Integer estoque,
        String descricao

) {
    public BrinquedoResponseDTO(Brinquedo brinquedo) {
        this(brinquedo.getId(), brinquedo.getNome(), brinquedo.getMarca(), brinquedo.getIdade(), brinquedo.getPreco(),
        brinquedo.getEstoque(), brinquedo.getDescricao()
        );
    }
}
