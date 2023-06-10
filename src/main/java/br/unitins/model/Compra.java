package br.unitins.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Compra extends DefaultEntity {

    private LocalDate data;
    private Double totalCompra;

    @ManyToOne
    private Brinquedo brinquedo;

    @OneToOne
    private ItemCompra itemCompra;

    public Brinquedo getBrinquedo() {
        return brinquedo;
    }

    public void setBrinquedo(Brinquedo brinquedo) {
        this.brinquedo = brinquedo;
    }

    public ItemCompra getItemCompra() {
        return itemCompra;
    }

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

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
