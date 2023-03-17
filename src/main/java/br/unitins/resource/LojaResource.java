package br.unitins.resource;

import java.util.List;

import javax.inject.Inject;

import javax.validation.Valid;
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

import br.unitins.dto.LojaDTO;
import br.unitins.dto.LojaResponseDTO;
import br.unitins.service.LojaService;

@Path("/lojas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LojaResource {

    @Inject
    private LojaService lojaService;

    @POST
    public Response insert(@Valid LojaDTO lojadto) {

        LojaResponseDTO brinquedo = lojaService.create(lojadto);
        return Response
                .status(Status.CREATED)
                .entity(brinquedo)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@Valid @PathParam("id") Long id, LojaDTO lojadto) {

        LojaResponseDTO brinquedo = lojaService.update(id, lojadto);
        return Response
                .status(Status.NO_CONTENT)
                .entity(brinquedo)
                .build();

    }

    @DELETE
    @Path("/{id}")
    public Response delete(@Valid @PathParam("id") Long id) {
        lojaService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    public LojaResponseDTO searchId(@PathParam("id") Long id) {
        return lojaService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<LojaResponseDTO> search(@PathParam("nome") String nome) {
        return lojaService.findByNome(nome);
    }

    @GET
    @Path("/count")
    public long count() {
        return lojaService.count();
    }

}
