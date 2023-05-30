package br.unitins.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Compra extends DefaultEntity {

    private LocalDate data;
    private Double totalCompra;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

}
