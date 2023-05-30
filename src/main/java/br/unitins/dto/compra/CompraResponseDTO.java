package br.unitins.dto.compra;

import java.time.LocalDate;

import br.unitins.model.Compra;

public record CompraResponseDTO (

    Long id,
    LocalDate data,
    Double totalCompra
){
    public CompraResponseDTO(Compra compra){
        this(compra.getId(), compra.getData(), compra.getTotalCompra());
    }
}
