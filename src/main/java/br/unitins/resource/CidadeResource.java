package br.unitins.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.unitins.application.Result;
import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.service.cidade.CidadeService;

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    CidadeService cidadeservice;

    @GET
    public List<CidadeResponseDTO> getAll() {
        return cidadeservice.getAll();
    }

    @GET
    @Path("/{id}")
    public CidadeResponseDTO findById(@PathParam("id") Long id) {
        return cidadeservice.findById(id);
    }

    @POST
    public Response insert(CidadeDTO cidadedto) {
        try {
            CidadeResponseDTO cidade = cidadeservice.create(cidadedto);
            return Response.status(Status.CREATED).entity(cidade).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CidadeDTO cidadedto) {
        try {
            CidadeResponseDTO cidade = cidadeservice.update(id, cidadedto);
            return Response.status(Status.NO_CONTENT).entity(cidade).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cidadeservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return cidadeservice.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<CidadeResponseDTO> search(@PathParam("nome") String nome) {
        return cidadeservice.findByNome(nome);

    }
}
