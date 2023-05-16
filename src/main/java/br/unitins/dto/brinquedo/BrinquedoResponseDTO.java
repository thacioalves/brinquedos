package br.unitins.dto.brinquedo;

import br.unitins.model.Brinquedo;

public record BrinquedoResponseDTO(

        Long id,
        String nome,
        String marca,
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
