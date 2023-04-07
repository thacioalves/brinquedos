package br.unitins.converterjpa;

import javax.persistence.AttributeConverter;

import br.unitins.model.Pagamento;

public class PagamentoConverter implements AttributeConverter<Pagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Pagamento pagamento) {
        return pagamento == null ? null : pagamento.getId();
    }

    @Override
    public Pagamento convertToEntityAttribute(Integer id) {
        return Pagamento.valueOf(id);
    }

}
