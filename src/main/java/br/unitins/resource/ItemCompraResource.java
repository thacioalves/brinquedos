package br.unitins.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.item.ItemCompraDTO;
import br.unitins.dto.item.ItemCompraResponseDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.item.ItemCompraService;
import br.unitins.service.usuario.UsuarioService;
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

@Path("/itemcompras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemCompraResource {

    @Inject
    ItemCompraService itemCompraService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(ItemCompraResource.class);

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
    public List<ItemCompraResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return itemCompraService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public ItemCompraResponseDTO findById(@PathParam("id") Long id) {
        return itemCompraService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(ItemCompraDTO itemCompraDTO) {
        LOG.infof("Inserindo um item compra: %s", itemCompraDTO.getClass());
        Result result = null;
        try {
            ItemCompraResponseDTO itemCompra = itemCompraService.create(itemCompraDTO);
            LOG.infof("Brinquedo (%d) criado com sucesso.", itemCompra.id());
            return Response.status(Status.CREATED).entity(itemCompra).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um item compra.");
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
    public Response update(@PathParam("id") Long id, ItemCompraDTO itemCompraDTO) {
        LOG.infof("Atualizando um item compra: %s", itemCompraDTO.getClass());
        Result result = null;
        try {
            ItemCompraResponseDTO itemCompra = itemCompraService.update(id, itemCompraDTO);
            LOG.infof("Brinquedo (%d) atualizado com sucesso.", itemCompra.id());
            return Response.status(Status.NO_CONTENT).entity(itemCompra).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar um brinquedo.");
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
        itemCompraService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public ItemCompraResponseDTO searchId(@PathParam("id") Long id) {
        return itemCompraService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<ItemCompraResponseDTO> search(@PathParam("nome") String nome) {
        return itemCompraService.findByNome(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return itemCompraService.count();
    }
}
