package br.unitins.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Pagamento extends DefaultEntity {

    private String valor;

    @OneToOne
    private Compra compra;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
