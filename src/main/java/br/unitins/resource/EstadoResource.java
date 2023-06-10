package br.unitins.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
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
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.estado.EstadoService;
import br.unitins.service.usuario.UsuarioService;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoservice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

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
    public List<EstadoResponseDTO> getAll() {
        LOG.info("Buscando todos os estados.");
        LOG.debug("ERRO DE DEBUG.");
        return estadoservice.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public EstadoResponseDTO findbyId(@PathParam("id") Long id) {
        return estadoservice.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(EstadoDTO estadodto) {
        LOG.infof("Inserindo um estado: %s", estadodto.nome());
        Result result = null;
        try {
            EstadoResponseDTO estado = estadoservice.create(estadodto);
            LOG.infof("Estado (%d) criado com sucesso.", estado.id());
            return Response.status(Status.CREATED).entity(estado).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um estado.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, EstadoDTO estadodto) {
        LOG.infof("Atualizando um estado: %s", estadodto.nome());
        Result result = null;
        try {
            EstadoResponseDTO estado = estadoservice.update(id, estadodto);
            LOG.infof("Estado (%d) atualizado com sucesso.", estado.id());
            return Response.status(Status.NO_CONTENT).entity(estado).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar um estado.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        estadoservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return estadoservice.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<EstadoResponseDTO> search(@PathParam("nome") String nome) {
        return estadoservice.findByNome(nome);

    }

}
