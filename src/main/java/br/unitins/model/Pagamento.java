package br.unitins.model;

import jakarta.persistence.Entity;

@Entity
public class Pagamento extends DefaultEntity{
    private String valor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
