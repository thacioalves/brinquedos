package br.unitins.service.item;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.dto.item.ItemCompraDTO;
import br.unitins.dto.item.ItemCompraResponseDTO;
import br.unitins.model.ItemCompra;
import br.unitins.repository.ItemCompraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ItemCompraServiceImpl implements ItemCompraService{

    @Inject
    ItemCompraRepository itemCompraRepository;

    @Inject
    Validator validator;

    @Override
    public List<ItemCompraResponseDTO> getAll() {
        List<ItemCompra> list = itemCompraRepository.listAll();
        return list.stream().map(ItemCompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ItemCompraResponseDTO findById(Long id) {
        ItemCompra itemCompra = itemCompraRepository.findById(id);
        if (itemCompra == null)
            return null;
        return new ItemCompraResponseDTO(itemCompra);
    }

    private void validar(ItemCompraDTO itemCompraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<ItemCompraDTO>> violations = validator.validate(itemCompraDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public ItemCompraResponseDTO create(ItemCompraDTO itemCompraDTO) throws ConstraintViolationException{
        validar(itemCompraDTO);

        ItemCompra entity = new ItemCompra();
        entity.setQuantidade(itemCompraDTO.quantidade());
        entity.setPreco(itemCompraDTO.preco());

        itemCompraRepository.persist(entity);
        return new ItemCompraResponseDTO(entity);
    }

    @Override
    @Transactional
    public ItemCompraResponseDTO update(Long id, ItemCompraDTO itemCompraDTO) throws ConstraintViolationException{
        ItemCompra itemUpdate = itemCompraRepository.findById(id);
        if (itemUpdate == null)
            throw new NotFoundException("Item Compra não encontrada.");
        validar(itemCompraDTO);

       itemUpdate.setQuantidade(itemCompraDTO.quantidade());
       itemUpdate.setPreco(itemCompraDTO.preco());

       itemCompraRepository.persist(itemUpdate);
        return new ItemCompraResponseDTO(itemUpdate);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemCompraRepository.deleteById(id);
    }

    @Override
    public List<ItemCompraResponseDTO> findByNome(String nome) {
        List<ItemCompra> list = itemCompraRepository.findByNome(nome);
        return list.stream().map(ItemCompraResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return itemCompraRepository.count();
    }
    
}
