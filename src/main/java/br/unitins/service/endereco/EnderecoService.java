package br.unitins.service.endereco;

import java.util.List;

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.endereco.EnderecoResponseDTO;

public interface EnderecoService {
    List<EnderecoResponseDTO> getAll();

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(EnderecoDTO enderecodto);

    EnderecoResponseDTO update(Long id, EnderecoDTO enderecodto);

    void delete(Long id);

    List<EnderecoResponseDTO> findByNome(String nome);

    long count();
}