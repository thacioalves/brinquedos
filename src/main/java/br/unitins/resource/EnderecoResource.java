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
