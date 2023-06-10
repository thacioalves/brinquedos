package br.unitins.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.usuario.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioservice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public Response getUsuario() {

        // obtendo o login a partir do token
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os Usuarios.");
        LOG.debug("ERRO DE DEBUG.");
        return usuarioservice.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public UsuarioResponseDTO findbyId(@PathParam("id") Long id) {
        return usuarioservice.findById(id);
    }

    // @POST
    // public Response insert(UsuarioDTO usuariodto) {
    // try {
    // UsuarioResponseDTO usuario = usuarioservice.create(usuariodto);
    // return Response.status(Status.CREATED).entity(usuario).build();
    // } catch (ConstraintViolationException e) {
    // Result result = new Result(e.getConstraintViolations());
    // return Response.status(Status.NOT_FOUND).entity(result).build();
    // }
    // }

    // @PUT
    // @Path("/{id}")
    // public Response update(@PathParam("id") Long id, UsuarioDTO usuariodto) {
    // try {
    // UsuarioResponseDTO usuario = usuarioservice.create(usuariodto);
    // return Response.status(Status.NO_CONTENT).entity(usuario).build();
    // } catch (ConstraintViolationException e) {
    // Result result = new Result(e.getConstraintViolations());
    // return Response.status(Status.NOT_FOUND).entity(result).build();
    // }
    // }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        usuarioservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return usuarioservice.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<UsuarioResponseDTO> search(@PathParam("nome") String nome) {
        return usuarioservice.findByNome(nome);

    }

}
