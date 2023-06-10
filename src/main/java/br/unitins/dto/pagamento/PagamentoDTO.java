package br.unitins.dto.pagamento;

import br.unitins.model.Compra;

public record PagamentoDTO(
    
    String valor,
    Compra compra

) {
    
}
