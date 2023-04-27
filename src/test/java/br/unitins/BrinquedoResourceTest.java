package br.unitins;

import br.unitins.dto.produto.ProdutoResponseDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.brinquedo.BrinquedoDTO;
import br.unitins.dto.brinquedo.BrinquedoResponseDTO;
import br.unitins.service.brinquedo.BrinquedoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

@QuarkusTest
public class BrinquedoResourceTest {

    @Inject
    BrinquedoService brinquedoservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/brinquedos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        BrinquedoDTO brinquedo = new BrinquedoDTO(
                "boneco",
                10.00,
                "6", "aleatoria", 30, "boneco de pelucia");

        BrinquedoResponseDTO brinquedocreate = brinquedoservice.create(brinquedo);
        given()
                .contentType(ContentType.JSON)
                .body(brinquedocreate)
                .when().post("/brinquedos")
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
        // Adicionando um brinquedo no banco de dados
        BrinquedoDTO brinquedo = new BrinquedoDTO(
                "boneco",
                10.00,
                "6", "aleatoria", 30, "boneco de pelucia");
        Long id = brinquedoservice.create(brinquedo).id();

        // Criando outro brinquedo para atuailzacao
        BrinquedoDTO brinquedoupdate = new BrinquedoDTO(
                "carrinho",
                15.00,
                "6", "aleatoria", 20, "carrinho hot wheels");

        BrinquedoResponseDTO brinquedoatualizado = brinquedoservice.update(id, brinquedoupdate);
        given()
                .contentType(ContentType.JSON)
                .body(brinquedoatualizado)
                .when().put("/brinquedos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        BrinquedoResponseDTO brinquedoresponse = brinquedoservice.findById(id);
        assertThat(brinquedoresponse.nome(), is("carrinho"));
        assertThat(brinquedoresponse.preco(), is(15.00));
        assertThat(brinquedoresponse.marca(), is("aleatoria"));
        assertThat(brinquedoresponse.estoque(), is(20));
        assertThat(brinquedoresponse.descricao(), is("carrinho hot wheels"));
    }

    @Test
    public void testDelete() {
        // Adicionando um brinquedo no banco de dados
        BrinquedoDTO brinquedo = new BrinquedoDTO(
                "boneco",
                10.00,
                "6", "aleatoria", 30, "boneco de pelucia");
        Long id = brinquedoservice.create(brinquedo).id();

        given()
                .when().delete("/brinquedos/" + id)
                .then()
                .statusCode(204);

        // verificando se o brinquedo foi excluido
        BrinquedoResponseDTO brinquedoresponse = null;
        try {
            brinquedoresponse = brinquedoservice.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(brinquedoresponse);
        }

    }
}