package br.unitins.dto;

import br.unitins.model.Compra;

public class CompraResponseDTO {

    private Long id;
    private Double totalCompra;

    public CompraResponseDTO(Compra compra) {
        this.id = compra.getId();
        this.totalCompra = compra.getTotalCompra();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

}
