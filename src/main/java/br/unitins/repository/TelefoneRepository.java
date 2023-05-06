package br.unitins.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import br.unitins.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

    public List<Telefone> findByNumero(String numero) {
        if (numero == null)
            return null;
        return find("UPPER(numero) LIKE ?1 ", "%" + numero.toUpperCase() + "%").list();
    }

}
