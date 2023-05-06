package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.estado.EstadoDTO;
import br.unitins.dto.estado.EstadoResponseDTO;
import br.unitins.service.estado.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    EstadoService estadoservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        EstadoDTO estado = new EstadoDTO(
                "Tocantins", "TO");

        EstadoResponseDTO estadocreate = estadoservice.create(estado);
        given()
                .contentType(ContentType.JSON)
                .body(estadocreate)
                .when().post("/estados")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("Tocantins"), "sigla", is("TO"));
    }

    @Test
    public void testUpdate() {
        // Adicionando um estado no banco de dados
        EstadoDTO estado = new EstadoDTO(
                "Tocantins", "TO");
        Long id = estadoservice.create(estado).id();

        // Criando outro estado para atuailzacao
        EstadoDTO estadoupdate = new EstadoDTO(
                "Sao Paulo", "SP");

        EstadoResponseDTO estadoatualizado = estadoservice.update(id, estadoupdate);
        given()
                .contentType(ContentType.JSON)
                .body(estadoatualizado)
                .when().put("/estados/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        EstadoResponseDTO estadoresponse = estadoservice.findById(id);
        assertThat(estadoresponse.nome(), is("Sao Paulo"));
        assertThat(estadoresponse.sigla(), is("SP"));
    }

    @Test
    public void testDelete() {
        // Adicionando um estado no banco de dados
        EstadoDTO estado = new EstadoDTO(
                "Tocantins", "TO");
        Long id = estadoservice.create(estado).id();
        given()
                .when().delete("/estados/" + id)
                .then()
                .statusCode(204);

        // verificando se o estado foi excluido
        EstadoResponseDTO estadoresponse = null;
        try {
            estadoresponse = estadoservice.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(estadoresponse);
        }

    }
}