package br.unitins.service.compra;

import java.util.List;

import br.unitins.dto.compra.CompraDTO;
import br.unitins.dto.compra.CompraResponseDTO;

public interface CompraService {
    
    List<CompraResponseDTO> getAll();

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(CompraDTO compradto);

    CompraResponseDTO update(Long id, CompraDTO compradto);

    void delete(Long id);

    List<CompraResponseDTO> findByNome(String nome);

    Long count();
}
