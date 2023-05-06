package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.usuario.UsuarioDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.endereco.EnderecoService;
import br.unitins.service.usuario.UsuarioService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

@QuarkusTest
public class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioservice;

    @Inject
    EnderecoService enderecoservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {

        List<TelefoneDTO> telefones = new ArrayList<>();
        telefones.add(new TelefoneDTO("63", "123456789"));

        List<EnderecoDTO> enderecos = new ArrayList<>();
        enderecos.add(new EnderecoDTO("603 N", "bairro", "12", "1", "12345678", 1L ));

        UsuarioDTO usuario = new UsuarioDTO(
            "jose", "123.123.123.12", "jose@gmail.com", "senha", 1, telefones, enderecos);

        UsuarioResponseDTO usuariocreate = usuarioservice.create(usuario);
        given()
                .contentType(ContentType.JSON)
                .body(usuariocreate)
                .when().post("/usuarios")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void testUpdate() {
        // Adicionando um usuario no banco de dados
        UsuarioDTO usuario = new UsuarioDTO();
        Long id = usuarioservice.create(usuario).id();

        // Criando outro usuario para atuailzacao
        UsuarioDTO usuarioupdate = new UsuarioDTO();

        UsuarioResponseDTO usuarioatualizado = usuarioservice.update(id, usuarioupdate);

        given()
                .contentType(ContentType.JSON)
                .body(usuarioatualizado)
                .when().put("/usuarios/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        UsuarioResponseDTO usuarioresponse = usuarioservice.findById(id);
    }

    @Test
    public void testDelete() {
        // Adicionando um usuario no banco de dados
        UsuarioDTO usuario = new UsuarioDTO();
        Long id = usuarioservice.create(usuario).id();
        given()
                .when().delete("/usuarios/" + id)
                .then()
                .statusCode(204);

        // verificando se o usuario foi excluida
        UsuarioResponseDTO usuarioresponse = null;
        try {
            usuarioresponse = usuarioservice.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(usuarioresponse);
        }

    }
}
