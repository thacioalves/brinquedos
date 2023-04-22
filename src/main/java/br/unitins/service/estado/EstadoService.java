package br.unitins.service.estado;

import java.util.List;

import br.unitins.dto.estado.EstadoDTO;
import br.unitins.dto.estado.EstadoResponseDTO;

public interface EstadoService {
    List<EstadoResponseDTO> getAll();

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO create(EstadoDTO estadodto);

    EstadoResponseDTO update(Long id, EstadoDTO estadodto);

    void delete(Long id);

    List<EstadoResponseDTO> findByNome(String nome);

    long count();
}
