package br.unitins.service.cidade;

import java.util.List;

import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.cidade.CidadeResponseDTO;

public interface CidadeService {

    List<CidadeResponseDTO> getAll();

    CidadeResponseDTO findById(Long id);

    CidadeResponseDTO create(CidadeDTO cidadedto);

    CidadeResponseDTO update(Long id, CidadeDTO cidadedto);

    void delete(Long id);

    List<CidadeResponseDTO> findByNome(String nome);

    long count();
}
