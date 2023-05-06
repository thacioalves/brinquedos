package br.unitins.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.model.Brinquedo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BrinquedoRepository implements PanacheRepository<Brinquedo> {
    
    public List<Brinquedo> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%").list();
    }

}
