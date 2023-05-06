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
import br.unitins.dto.estado.EstadoDTO;
import br.unitins.dto.estado.EstadoResponseDTO;
import br.unitins.service.estado.EstadoService;


@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoservice;

    @GET
    public List<EstadoResponseDTO> getAll() {
        return estadoservice.getAll();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO findbyId(@PathParam("id") Long id) {
        return estadoservice.findById(id);
    }

    @POST
    public Response insert(EstadoDTO estadodto) {
        try {
            EstadoResponseDTO estado = estadoservice.create(estadodto);
            return Response.status(Status.CREATED).entity(estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EstadoDTO estadodto) {
        try {
            EstadoResponseDTO estado = estadoservice.create(estadodto);
            return Response.status(Status.NO_CONTENT).entity(estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        estadoservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return estadoservice.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<EstadoResponseDTO> search(@PathParam("nome") String nome) {
        return estadoservice.findByNome(nome);

    }

}
