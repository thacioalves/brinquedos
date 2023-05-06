package br.unitins.service.produto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.unitins.dto.produto.ProdutoDTO;
import br.unitins.dto.produto.ProdutoResponseDTO;
import br.unitins.model.Produto;
import br.unitins.repository.ProdutoRepository;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtorepository;

    @Inject
    Validator validator;

    @Override
    public List<ProdutoResponseDTO> getAll() {
        List<Produto> list = produtorepository.listAll();
        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public ProdutoResponseDTO findById(long id) {
        Produto produto = produtorepository.findById(id);
        if (produto == null)
            throw new NotFoundException("Produto não encontrado.");
        return new ProdutoResponseDTO(produto);
    }

    private void validar(ProdutoDTO produtodto) throws ConstraintViolationException {
        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtodto);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public ProdutoResponseDTO create(ProdutoDTO produtodto) throws ConstraintViolationException {
        validar(produtodto);

        Produto entity = new Produto();
        entity.setNome(produtodto.nome());
        entity.setPreco(produtodto.preco());
        entity.setDescricao(produtodto.descricao());
        entity.setEstoque(produtodto.estoque());

        produtorepository.persist(entity);
        return new ProdutoResponseDTO(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(long id, ProdutoDTO produtodto) throws ConstraintViolationException {
        Produto produtoUpdate = produtorepository.findById(id);
        if (produtoUpdate == null)
            throw new NotFoundException("Produto não encontrado.");
        validar(produtodto);

        produtoUpdate.setNome(produtodto.nome());
        produtoUpdate.setPreco(produtodto.preco());
        produtoUpdate.setDescricao(produtodto.descricao());
        produtoUpdate.setEstoque(produtodto.estoque());

        produtorepository.persist(produtoUpdate);
        return new ProdutoResponseDTO(produtoUpdate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        produtorepository.deleteById(id);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome) {
        List<Produto> list = produtorepository.findByNome(nome);
        return list.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return produtorepository.count();
    }

}
