package br.unitins;

iimport io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.produto.ProdutoDTO;
import br.unitins.dto.produto.ProdutoResponseDTO;
import br.unitins.dto.usuario.UsuarioDTO;
import br.unitins.dto.usuario.UsuarioResponseDTO;
import br.unitins.service.produto.ProdutoService;
import br.unitins.service.usuario.UsuarioService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

@QuarkusTest
public class UsuarioResourceTest {

    @Inject
    UsuarioService usuarioservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        UsuarioDTO usuario = new UsuarioDTO();

        UsuarioResponseDTO usuariocreate = usuarioservice.create(usuario);
        given()
                .contentType(ContentType.JSON)
                .body(usuariocreate)
                .when().post("/usuarios")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("boneco"), "preco", is(10.00F),
                        "idade", is("6"), "marca", is("aleatoria"), "estoque", is(30),
                        "descricao",
                        is("boneco de pelucia"));
    }

    @Test
    public void testUpdate() {
        // Adicionando um Usuario no banco de dados
        UsuarioDTO usuario = new UsuarioDTO(
              );
        Long id = usuarioservice.create(usuario).id();

        // Criando outro brinquedo para atuailzacao
        UsuarioDTO usuarioupdate = new UsuarioDTO(
               );

        UsuarioResponseDTO usuarioatualizado = usuarioservice.update(id, usuarioupdate);
        given()
                .contentType(ContentType.JSON)
                .body(usuarioatualizado)
                .when().put("/usuarios/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        UsuarioResponseDTO usuarioresponse = usuarioservice.findById(id);
        assertThat(brinquedoresponse.nome(), is("carrinho"));
        assertThat(brinquedoresponse.preco(), is(15.00));
        assertThat(brinquedoresponse.marca(), is("aleatoria"));
        assertThat(brinquedoresponse.estoque(), is(20));
        assertThat(brinquedoresponse.descricao(), is("carrinho hot wheels"));
    }

    @Test
    public void testDelete() {
        // Adicionando um brinquedo no banco de dados
        UsuarioDTO usuario = new UsuarioDTO(
               );
        Long id = usuarioservice.create(usuario).id();

        given()
                .when().delete("/usuarios/" + id)
                .then()
                .statusCode(204);

        // verificando se o brinquedo foi excluido
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
