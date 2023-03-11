package br.unitins.resource;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.unitins.model.Loja;
import br.unitins.repository.LojaRepository;

@Path("/lojas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LojaResource {

    @Inject
    private LojaRepository lojarepository;

    @GET
    public List<Loja> getAll() {

        return lojarepository.findAll().list();

    }

    @POST
    @Transactional
    public Loja insert(Loja loja) {

        lojarepository.persist(loja);

        return loja;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Loja update(@PathParam("id") Long id, Loja loja) {

        Loja entity = lojarepository.findById(id);

        entity.setNome(loja.getNome());
        entity.setEstado(loja.getEstado());
        entity.setCidade(loja.getCidade());
        entity.setEndereco(loja.getEndereco());

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Loja delete(@PathParam("id") Long id) {

        Loja entity = lojarepository.findById(id);

        lojarepository.delete(entity);

        return entity;
    }

    @GET
    @Path("/search/{id}")
    public Loja searchId(@PathParam("id") Long id) {
        return lojarepository.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<Loja> search(@PathParam("nome") String nome) {
        return lojarepository.findByNome(nome);
    }
}
