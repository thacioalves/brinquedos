package br.unitins.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
            CidadeResponseDTO cidade = cidadeservice.create(cidadedto);
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
