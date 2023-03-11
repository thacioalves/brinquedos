package br.unitins.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.unitins.model.Loja;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LojaRepository implements PanacheRepository<Loja> {
    
    public List<Loja> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }
    
}
