package br.unitins.dto.pagamento;

import br.unitins.model.Compra;
import br.unitins.model.Pagamento;

public record PagamentoResponseDTO(
    
    Long id,
    String valor,
    Compra compra

) {
    public PagamentoResponseDTO(Pagamento pagamento){
        this(pagamento.getId(), pagamento.getValor(), pagamento.getCompra());
    }
}
