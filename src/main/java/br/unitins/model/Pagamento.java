package br.unitins.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public enum Pagamento {
    PIX(1, "Pix"),
    CREDITO(2, "Crédito"),
    DEBITO(3, "Débito");

    private int id;
    private String descricao;

    Pagamento(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Pagamento valueOf(Integer id) throws IllegalArgumentException {
        if (id == null)
            return null;
        for (Pagamento pagamento : Pagamento.values()) {
            if (id.equals(pagamento.getId()))
                return pagamento;
        }
        throw new IllegalArgumentException("Id inválido" + id);
    }
}
