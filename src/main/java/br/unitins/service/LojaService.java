package br.unitins.service;

import java.util.List;

import br.unitins.dto.LojaDTO;
import br.unitins.dto.LojaResponseDTO;

public interface LojaService {
    List<LojaResponseDTO> getAll();

    LojaResponseDTO findById(long id);

    LojaResponseDTO create(LojaDTO lojadto);

    LojaResponseDTO update(long id, LojaDTO lojadto);

    void delete(long id);

    List<LojaResponseDTO> findByNome(String nome);

    long count();

}
