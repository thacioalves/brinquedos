package br.unitins.service.item;

import java.util.List;

import br.unitins.dto.item.ItemCompraDTO;
import br.unitins.dto.item.ItemCompraResponseDTO;

public interface ItemCompraService {

    List<ItemCompraResponseDTO> getAll();

    ItemCompraResponseDTO findById(Long id);

    ItemCompraResponseDTO create(ItemCompraDTO itemCompraDTO);

    ItemCompraResponseDTO update(Long id, ItemCompraDTO itemCompraDTO);

    void delete(Long id);

    List<ItemCompraResponseDTO> findByNome(String nome);

    Long count();
}
