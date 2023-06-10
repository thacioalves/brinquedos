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
import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.cidade.CidadeService;
import br.unitins.service.usuario.UsuarioService;

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    CidadeService cidadeservice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

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
    public List<CidadeResponseDTO> getAll() {
        LOG.info("Buscando todas as cidades.");
        LOG.debug("ERRO DE DEBUG.");
        return cidadeservice.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public CidadeResponseDTO findById(@PathParam("id") Long id) {
        return cidadeservice.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(CidadeDTO cidadedto) {
        LOG.infof("Inserindo uma cidade: %s", cidadedto.nome());
        Result result = null;
        try {
            CidadeResponseDTO cidade = cidadeservice.create(cidadedto);
            LOG.infof("Cidade (%d) criada com sucesso.", cidade.id());
            return Response.status(Status.CREATED).entity(cidade).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma cidade.");
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
    public Response update(@PathParam("id") Long id, CidadeDTO cidadedto) {
        LOG.infof("Atualizando uma cidade: %s", cidadedto.nome());
        Result result = null;
        try {
            CidadeResponseDTO cidade = cidadeservice.update(id, cidadedto);
            LOG.infof("Cidade (%d) atualizada com sucesso.", cidade.id());
            return Response.status(Status.NO_CONTENT).entity(cidade).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma cidade.");
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
        cidadeservice.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return cidadeservice.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<CidadeResponseDTO> search(@PathParam("nome") String nome) {
        return cidadeservice.findByNome(nome);

    }
}
