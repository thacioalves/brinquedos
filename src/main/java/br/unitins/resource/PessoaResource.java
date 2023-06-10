package br.unitins.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.application.Result;
import br.unitins.dto.pessoa.PessoaDTO;
import br.unitins.dto.pessoa.PessoaResponseDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.pessoa.PessoaService;
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

@Path("/pessoas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaResource {
    
    @Inject
    PessoaService pessoaService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(PessoaResource.class);

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
    public List<PessoaResponseDTO> getAll() {
        LOG.info("Buscando todas as pessoas.");
        LOG.debug("ERRO DE DEBUG.");
        return pessoaService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public PessoaResponseDTO findById(@PathParam("id") Long id) {
        return pessoaService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PessoaDTO pessoaDTO) {
        LOG.infof("Inserindo uma cidade: %s", pessoaDTO.nome());
        Result result = null;
        try {
            PessoaResponseDTO pessoa = pessoaService.create(pessoaDTO);
            LOG.infof("Pessoa (%d) criada com sucesso.", pessoa.id());
            return Response.status(Status.CREATED).entity(pessoa).build();
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
    public Response update(@PathParam("id") Long id, PessoaDTO pessoaDTO) {
        LOG.infof("Atualizando uma pessoa: %s", pessoaDTO.nome());
        Result result = null;
        try {
            PessoaResponseDTO pessoa = pessoaService.update(id, pessoaDTO);
            LOG.infof("Pessoa (%d) atualizada com sucesso.", pessoa.id());
            return Response.status(Status.NO_CONTENT).entity(pessoa).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma pessoa.");
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
        pessoaService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pessoaService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<PessoaResponseDTO> search(@PathParam("nome") String nome) {
        return pessoaService.findByNome(nome);

    }
}
