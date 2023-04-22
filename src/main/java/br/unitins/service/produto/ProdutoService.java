package br.unitins.service.produto;

import java.util.List;

import br.unitins.dto.produto.ProdutoDTO;
import br.unitins.dto.produto.ProdutoResponseDTO;

public interface ProdutoService {
    List<ProdutoResponseDTO> getAll();

    ProdutoResponseDTO findById(long id);

    ProdutoResponseDTO create(ProdutoDTO produtodto);

    ProdutoResponseDTO update(long id, ProdutoDTO produtodto);

    void delete(long id);

    List<ProdutoResponseDTO> findByNome(String nome);

    long count();
}
