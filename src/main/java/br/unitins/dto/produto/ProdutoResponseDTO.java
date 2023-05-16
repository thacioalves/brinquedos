package br.unitins.dto.produto;

import br.unitins.model.Produto;

public record ProdutoResponseDTO(

        Long id,
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
