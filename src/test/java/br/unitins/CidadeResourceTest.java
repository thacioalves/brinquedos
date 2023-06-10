package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.cidade.CidadeResponseDTO;
import br.unitins.dto.estado.EstadoDTO;
import br.unitins.model.Estado;
import br.unitins.service.cidade.CidadeService;
import br.unitins.service.estado.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class CidadeResourceTest {

    @Inject
    CidadeService cidadeservice;

    @Inject
    EstadoService estadoservice;

    private String token;

    @BeforeEach
    public void setUp() {
        var auth = new AuthUsuarioDTO("teste", "123");

        Response response = (Response) given()
                .contentType("application/json")
                .body(auth)
                .when().post("/auth")
                .then().statusCode(200)
                .extract()
                .response();

        token = response.header("Authorization");
    }

    @Test
    public void testGetAll() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cidades")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        Long id = estadoservice.create(new EstadoDTO("Tocantins", "TO")).id();
        CidadeDTO cidade = new CidadeDTO("Palmas", id);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(cidade)
                .when().post("/cidades")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Palmas"),
                        "estado", notNullValue(Estado.class));
    }

    @Test
    public void testUpdate() {
        // Adicionando uma cidade no banco de dados
        Long id = estadoservice.create(new EstadoDTO("Tocantins", "TO")).id();
        CidadeDTO cidade = new CidadeDTO("Palmas", id);
        Long idCidade = cidadeservice.create(cidade).id();

        // Criando outra cidade para atualizacao
        CidadeDTO cidadeupdate = new CidadeDTO(
                "Paraiso", id);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(cidadeupdate)
                .when().put("/cidades/" + idCidade)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        CidadeResponseDTO cidaderesponse = cidadeservice.findById(id);
        assertThat(cidaderesponse.nome(), is("Paraiso"));
        assertThat(cidaderesponse.estado(), notNullValue());
    }

    @Test
    public void testDelete() {
        // Adicionando uma cidade no banco de dados
        Long id = estadoservice.create(new EstadoDTO("Tocantins", "TO")).id();
        CidadeDTO cidade = new CidadeDTO(
                "Palmas", 1L);
        Long idCidade = cidadeservice.create(cidade).id();
        given()
                .header("Authorization", "Bearer " + token)
                .when().delete("/cidades/" + idCidade)
                .then()
                .statusCode(204);

        // verificando se a cidade foi excluida
        CidadeResponseDTO cidaderesponse = null;
        try {
            cidaderesponse = cidadeservice.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(cidaderesponse);
        }

    }

    @Test
    public void testFindById() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cidades/1")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testCount() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cidades/count")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testSearch() {
        given()
                .header("Authorization", "Bearer " + token)
                .when().get("/cidades/search/palmas")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}