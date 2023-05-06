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
import br.unitins.dto.usuario.UsuarioDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.usuario.UsuarioService;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioservice;

    @GET
    public List<UsuarioResponseDTO> getAll() {
        return usuarioservice.getAll();
    }

    @GET
    @Path("/{id}")
    public UsuarioResponseDTO findbyId(@PathParam("id") Long id) {
        return usuarioservice.findById(id);
    }

    @POST
    public Response insert(UsuarioDTO usuariodto) {
        try {
            UsuarioResponseDTO usuario = usuarioservice.create(usuariodto);
            return Response.status(Status.CREATED).entity(usuario).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UsuarioDTO usuariodto) {
        try {
            UsuarioResponseDTO usuario = usuarioservice.create(usuariodto);
            return Response.status(Status.NO_CONTENT).entity(usuario).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        usuarioservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return usuarioservice.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<UsuarioResponseDTO> search(@PathParam("nome") String nome) {
        return usuarioservice.findByNome(nome);

    }

}
