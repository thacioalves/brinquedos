package br.unitins.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.pagamento.PagamentoDTO;
import br.unitins.dto.pagamento.PagamentoResponseDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.pagamento.PagamentoService;
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

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

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
    public List<PagamentoResponseDTO> getAll() {
        LOG.info("Buscando todos os pagamentos.");
        LOG.debug("ERRO DE DEBUG.");
        return pagamentoService.getAll();

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public PagamentoResponseDTO findById(@PathParam("id") Long id) {
        return pagamentoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PagamentoDTO pagamentoDTO) {
        LOG.infof("Inserindo um pagamento: %s", pagamentoDTO.valor(), pagamentoDTO.compra());
        Result result = null;
        try {
            PagamentoResponseDTO pagamento = pagamentoService.create(pagamentoDTO);
            LOG.infof("pagamento (%d) criado com sucesso.", pagamento.id());
            return Response.status(Status.CREATED).entity(pagamento).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um pagamento.");
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
    public Response update(@PathParam("id") Long id, PagamentoDTO pagamentoDTO) {
        LOG.infof("Atualizando um pagamento: %s", pagamentoDTO.valor(), pagamentoDTO.compra());
        Result result = null;
        try {
            PagamentoResponseDTO pagamento = pagamentoService.update(id, pagamentoDTO);
            LOG.infof("pagamento (%d) atualizado com sucesso.", pagamento.id());
            return Response.status(Status.NO_CONTENT).entity(pagamento).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar um pagamento.");
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
        pagamentoService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public PagamentoResponseDTO searchId(@PathParam("id") Long id) {
        return pagamentoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<PagamentoResponseDTO> search(@PathParam("pagamento") String nome) {
        return pagamentoService.findByPagamentos(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pagamentoService.count();
    }

}
