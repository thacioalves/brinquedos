package br.unitins.dto.compra;

import java.time.LocalDate;

public record CompraDTO(

    LocalDate data,
    Double totalCompra 
) {
    
}
