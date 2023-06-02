package br.unitins.resource;

import org.jboss.logging.Logger;

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
import br.unitins.dto.brinquedo.BrinquedoDTO;
import br.unitins.dto.brinquedo.BrinquedoResponseDTO;
import br.unitins.service.brinquedo.BrinquedoService;

import java.util.List;

@Path("/brinquedos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BrinquedoResource {

    @Inject
    BrinquedoService brinquedoService;

    private static final Logger LOG = Logger.getLogger(BrinquedoResource.class);

    @GET
    public List<BrinquedoResponseDTO> getAll() {
        LOG.info("Buscando todos os brinquedos.");
        LOG.debug("ERRO DE DEBUG.");
        return brinquedoService.getAll();

    }

    @GET
    @Path("/{id}")
    public BrinquedoResponseDTO findById(@PathParam("id") Long id) {
        return brinquedoService.findById(id);
    }

    @POST
    public Response insert(BrinquedoDTO brinquedodto) {
        LOG.infof("Inserindo um brinquedo: %s", brinquedodto.nome());
        Result result = null;
        try {
            BrinquedoResponseDTO brinquedo = brinquedoService.create(brinquedodto);
            LOG.infof("Brinquedo (%d) criado com sucesso.", brinquedo.id());
            return Response.status(Status.CREATED).entity(brinquedo).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um brinquedo.");
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
    public Response update(@PathParam("id") Long id, BrinquedoDTO brinquedodto) {
        LOG.infof("Atualizando um brinquedo: %s", brinquedodto.nome());
        Result result = null;
        try {
            BrinquedoResponseDTO brinquedo = brinquedoService.update(id, brinquedodto);
            LOG.infof("Brinquedo (%d) atualizado com sucesso.", brinquedo.id());
            return Response.status(Status.NO_CONTENT).entity(brinquedo).build();
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
    public Response delete(@PathParam("id") Long id) {
        brinquedoService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    public BrinquedoResponseDTO searchId(@PathParam("id") Long id) {
        return brinquedoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<BrinquedoResponseDTO> search(@PathParam("nome") String nome) {
        return brinquedoService.findByNome(nome);
    }

    @GET
    @Path("/count")
    public long count() {
        return brinquedoService.count();
    }

}
