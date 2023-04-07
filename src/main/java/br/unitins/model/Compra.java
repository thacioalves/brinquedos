package br.unitins.model;

import javax.persistence.Entity;

@Entity
public class Compra extends DefaultEntity{
    private Double totalCompra;

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

}
