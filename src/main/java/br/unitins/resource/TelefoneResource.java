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
import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.telefone.TelefoneService;
import br.unitins.service.usuario.UsuarioService;

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService telefoneservice;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

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
    public List<TelefoneResponseDTO> getAll() {
        LOG.info("Buscando todos os telefones.");
        LOG.debug("ERRO DE DEBUG.");
        return telefoneservice.getAll();

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public TelefoneResponseDTO findById(@PathParam("id") Long id) {
        return telefoneservice.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(TelefoneDTO telefonedto) {
        LOG.infof("Inserindo um telefone: %s", telefonedto.codigoArea(), telefonedto.numero());
        Result result = null;
        try {
            TelefoneResponseDTO telefone = telefoneservice.create(telefonedto);
            LOG.infof("Telefone (%d) criado com sucesso.", telefone.id());
            return Response.status(Status.CREATED).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um telefone.");
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
    public Response update(@PathParam("id") Long id, TelefoneDTO telefonedto) {
        LOG.infof("Atualizando um telefone: %s", telefonedto.codigoArea(), telefonedto.numero());
        Result result = null;
        try {
            TelefoneResponseDTO telefone = telefoneservice.update(id, telefonedto);
            LOG.infof("Telefone (%d) atualizado com sucesso.", telefone.id());
            return Response.status(Status.NO_CONTENT).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar um telefone.");
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
        telefoneservice.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public TelefoneResponseDTO searchId(@PathParam("id") Long id) {
        return telefoneservice.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<TelefoneResponseDTO> search(@PathParam("nome") String nome) {
        return telefoneservice.findByNumero(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return telefoneservice.count();
    }

}
