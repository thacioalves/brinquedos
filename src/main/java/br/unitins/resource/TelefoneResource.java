package br.unitins.resource;

import java.util.List;

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
import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;
import br.unitins.service.telefone.TelefoneService;

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService telefoneservice;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @GET
    public List<TelefoneResponseDTO> getAll() {
        LOG.info("Buscando todos os telefones.");
        LOG.debug("ERRO DE DEBUG.");
        return telefoneservice.getAll();

    }

    @GET
    @Path("/{id}")
    public TelefoneResponseDTO findById(@PathParam("id") Long id) {
        return telefoneservice.findById(id);
    }

    @POST
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
    public Response delete(@PathParam("id") Long id) {
        telefoneservice.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();
    }

    @GET
    @Path("/search/{id}")
    public TelefoneResponseDTO searchId(@PathParam("id") Long id) {
        return telefoneservice.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<TelefoneResponseDTO> search(@PathParam("nome") String nome) {
        return telefoneservice.findByNumero(nome);
    }

    @GET
    @Path("/count")
    public long count() {
        return telefoneservice.count();
    }

}
