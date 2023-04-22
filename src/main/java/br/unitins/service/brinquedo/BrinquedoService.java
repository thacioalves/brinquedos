package br.unitins.service.brinquedo;

import java.util.List;

import br.unitins.dto.brinquedo.BrinquedoDTO;
import br.unitins.dto.brinquedo.BrinquedoResponseDTO;

public interface BrinquedoService {
    List<BrinquedoResponseDTO> getAll();

    BrinquedoResponseDTO findById(long id);

    BrinquedoResponseDTO create(BrinquedoDTO brinquedodto);

    BrinquedoResponseDTO update(long id, BrinquedoDTO brinquedodto);

    void delete(long id);

    List<BrinquedoResponseDTO> findByNome(String nome);

    long count();
}
