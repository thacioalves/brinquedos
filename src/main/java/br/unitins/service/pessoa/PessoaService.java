package br.unitins.service.pessoa;

import java.util.List;

import br.unitins.dto.pessoa.PessoaDTO;
import br.unitins.dto.pessoa.PessoaResponseDTO;

public interface PessoaService {
    
    List<PessoaResponseDTO> getAll();

    PessoaResponseDTO findById(Long id);

    PessoaResponseDTO create(PessoaDTO pessoaDTO);

    PessoaResponseDTO update(Long id, PessoaDTO pessoaDTO);

    void delete(Long id);

    List<PessoaResponseDTO> findByNome(String nome);

    long count();

}
