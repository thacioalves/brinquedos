package br.unitins.resource;

import java.util.List;
import java.util.stream.Collectors;

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

import br.unitins.dto.BrinquedoDTO;
import br.unitins.dto.BrinquedoResponseDTO;
import br.unitins.model.Brinquedo;
import br.unitins.repository.BrinquedoRepository;
import br.unitins.repository.LojaRepository;

@Path("/brinquedos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BrinquedoResource {

    @Inject
    private BrinquedoRepository brinquedorepository;

    @Inject
    private LojaRepository lojaRepository;

    @GET
    public List<BrinquedoResponseDTO> getAll() {

        return brinquedorepository.findAll()
                .stream()
                .map(brinquedo -> new BrinquedoResponseDTO(brinquedo))
                .collect(Collectors.toList());

    }

    @POST
    @Transactional
    public BrinquedoResponseDTO insert(BrinquedoDTO brinquedodto) {

        Brinquedo entity = new Brinquedo();
        entity.setNome(brinquedodto.getNome());
        entity.setMarca(brinquedodto.getMarca());
        entity.setLoja(lojaRepository.findById(brinquedodto.getIdLoja()));

        brinquedorepository.persist(entity);

        return new BrinquedoResponseDTO(entity);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Brinquedo update(@PathParam("id") Long id, Brinquedo brinquedo) {

        Brinquedo entity = brinquedorepository.findById(id);

        entity.setNome(brinquedo.getNome());
        entity.setMarca(brinquedo.getMarca());
        entity.setLoja(brinquedo.getLoja());
        entity.setIdade(brinquedo.getIdade());

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Brinquedo delete(@PathParam("id") Long id) {

        Brinquedo entity = brinquedorepository.findById(id);

        brinquedorepository.delete(entity);

        return entity;
    }

    @GET
    @Path("/search/{id}")
    public Brinquedo searchId(@PathParam("id") Long id) {
        return brinquedorepository.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List <Brinquedo> search(@PathParam("nome") String nome) {
        return brinquedorepository.findByNome(nome);
    }

}
