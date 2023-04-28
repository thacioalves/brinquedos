package br.unitins.resource;

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
import br.unitins.dto.brinquedo.BrinquedoDTO;
import br.unitins.dto.brinquedo.BrinquedoResponseDTO;
import br.unitins.service.brinquedo.BrinquedoService;

import java.util.List;

@Path("/brinquedos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BrinquedoResource {

    @Inject
    BrinquedoService brinquedoService;

    @GET
    public List<BrinquedoResponseDTO> getAll() {
        return brinquedoService.getAll();

    }

    @GET
    @Path("/{id}")
    public BrinquedoResponseDTO findById(@PathParam("id") long id) {
        return brinquedoService.findById(id);
    }

    @POST
    public Response insert(BrinquedoDTO brinquedodto) {
        try {
            BrinquedoResponseDTO brinquedo = brinquedoService.create(brinquedodto);
            return Response.status(Status.CREATED).entity(brinquedo).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, BrinquedoDTO brinquedodto) {
        try {
            BrinquedoResponseDTO brinquedo = brinquedoService.create(brinquedodto);
            return Response.status(Status.NO_CONTENT).entity(brinquedo).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        brinquedoService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    public BrinquedoResponseDTO searchId(@PathParam("id") Long id) {
        return brinquedoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<BrinquedoResponseDTO> search(@PathParam("nome") String nome) {
        return brinquedoService.findByNome(nome);
    }

    @GET
    @Path("/count")
    public long count() {
        return brinquedoService.count();
    }

}
