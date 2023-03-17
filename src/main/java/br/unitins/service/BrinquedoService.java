package br.unitins.service;

import java.util.List;

import br.unitins.dto.BrinquedoDTO;
import br.unitins.dto.BrinquedoResponseDTO;

public interface BrinquedoService {
    List<BrinquedoResponseDTO> getAll();

    BrinquedoResponseDTO findById(long id);

    BrinquedoResponseDTO create(BrinquedoDTO brinquedodto);

    BrinquedoResponseDTO update(long id, BrinquedoDTO brinquedodto);

    void delete(long id);

    List<BrinquedoResponseDTO> findByNome(String nome);

    long count();
}
