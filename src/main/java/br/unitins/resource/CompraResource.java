package br.unitins.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.compra.CompraDTO;
import br.unitins.dto.compra.CompraResponseDTO;
import br.unitins.service.compra.CompraService;
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

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    private static final Logger LOG = Logger.getLogger(BrinquedoResource.class);

    @GET
    public List<CompraResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return compraService.getAll();
    }

    @GET
    @Path("/{id}")
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @POST
    public Response insert(CompraDTO compradto) {
        try {
            CompraResponseDTO compra = compraService.create(compradto);
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();

        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CompraDTO compradto) {
        try {
            CompraResponseDTO compra = compraService.update(id, compradto);
            return Response.status(Status.NO_CONTENT).entity(compra).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();

        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        compraService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

    @GET
    @Path("/search/{id}")
    public CompraResponseDTO searchId(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<CompraResponseDTO> search(@PathParam("nome") String nome) {
        return compraService.findByNome(nome);
    }

    @GET
    @Path("/count")
    public long count() {
        return compraService.count();
    }
}
