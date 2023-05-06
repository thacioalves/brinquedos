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
import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.endereco.EnderecoResponseDTO;
import br.unitins.service.endereco.EnderecoService;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    @Inject
    EnderecoService enderecoservice;

    @GET
    public List<EnderecoResponseDTO> getAll() {
        return enderecoservice.getAll();
    }

    @GET
    @Path("/{id}")
    public EnderecoResponseDTO findbyId(@PathParam("id") Long id) {
        return enderecoservice.findById(id);
    }

    @POST
    public Response insert(EnderecoDTO enderecodto) {
        try {
            EnderecoResponseDTO endereco = enderecoservice.create(enderecodto);
            return Response.status(Status.CREATED).entity(endereco).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EnderecoDTO enderecodto) {
        try {
            EnderecoResponseDTO endereco = enderecoservice.update(id, enderecodto);
            return Response.status(Status.NO_CONTENT).entity(endereco).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        enderecoservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return enderecoservice.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<EnderecoResponseDTO> search(@PathParam("nome") String nome) {
        return enderecoservice.findByNome(nome);

    }

}
