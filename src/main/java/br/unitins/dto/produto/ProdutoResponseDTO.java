package br.unitins.dto.produto;

import javax.validation.constraints.NotBlank;

import br.unitins.model.Produto;

public record ProdutoResponseDTO(

        Long id,

        @NotBlank(message = "O campo nome deve ser informado.")
        String nome,
        
        String descricao,
        Double preco,
        Integer estoque

) {
    public ProdutoResponseDTO(Produto produto){
        this(produto.getId(), produto.getNome(), produto.getDescricao(),
        produto.getPreco(), produto.getEstoque()
        );
    }
}
