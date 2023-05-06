package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

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

import jakarta.inject.Inject;

@QuarkusTest
public class CidadeResourceTest {

    @Inject
    CidadeService cidadeservice;

    @Inject
    EstadoService estadoservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/cidades")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        Long id = estadoservice.create(new EstadoDTO("Tocantins", "TO")).id();
        CidadeDTO cidade = new CidadeDTO("Palmas", id);

        given()
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

        // Criando outra cidade para atuailzacao
        CidadeDTO cidadeupdate = new CidadeDTO(
                "Paraiso", id);

        given()
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
}