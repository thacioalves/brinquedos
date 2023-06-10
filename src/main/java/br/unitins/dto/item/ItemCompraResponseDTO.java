package br.unitins.dto.item;

import br.unitins.model.ItemCompra;

public record ItemCompraResponseDTO(

    Long id,
    Integer quantidade,
    float preco
) {
    public ItemCompraResponseDTO(ItemCompra itemCompra){
        this(itemCompra.getId(), itemCompra.getQuantidade(), itemCompra.getPreco());
    }
}
