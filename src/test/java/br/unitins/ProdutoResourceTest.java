package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.produto.ProdutoDTO;
import br.unitins.dto.produto.ProdutoResponseDTO;
import br.unitins.service.produto.ProdutoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.inject.Inject;

@QuarkusTest
public class ProdutoResourceTest {

    @Inject
    ProdutoService produtoservice;

    @Test
    public void testGetAll() {
        given()
                .when().get("/produtos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        ProdutoDTO produto = new ProdutoDTO(
                "banco imobiliario", "jogo de tabuleiro", 50.00, 3);

        ProdutoResponseDTO produtocreate = produtoservice.create(produto);
        given()
                .contentType(ContentType.JSON)
                .body(produtocreate)
                .when().post("/produtos")
                .then()
                .statusCode(201)
                .body("id", notNullValue(), "nome", is("banco imobiliario"), "descricao",
                        is("jogo de tabuleiro"),
                        "preco", is(50.00F), "estoque", is(3));
    }

    @Test
    public void testUpdate() {
        // Adicionando um produto no banco de dados
        ProdutoDTO produto = new ProdutoDTO(
                "banco imobiliario", "jogo de tabuleiro", 50.00, 3);
        Long id = produtoservice.create(produto).id();

        // Criando outro produto para atuailzacao
        ProdutoDTO produtoupdate = new ProdutoDTO(
                "Batman", "lego batman", 30.00, 4);

        ProdutoResponseDTO produtoatualizado = produtoservice.update(id, produtoupdate);

        given()
                .contentType(ContentType.JSON)
                .body(produtoatualizado)
                .when().put("/produtos/" + id)
                .then()
                .statusCode(204);

        // Verificando se os dados foram atualizados no banco de dados
        ProdutoResponseDTO produtoresponse = produtoservice.findById(id);
        assertThat(produtoresponse.nome(), is("Batman"));
        assertThat(produtoresponse.descricao(), is("lego batman"));
        assertThat(produtoresponse.preco(), is(30.00));
        assertThat(produtoresponse.estoque(), is(4));
    }

    @Test
    public void testDelete() {
        // Adicionando um produto no banco de dados
        ProdutoDTO produto = new ProdutoDTO(
                "banco imobiliario", "jogo de tabuleiro", 50.00, 3);
        Long id = produtoservice.create(produto).id();
        given()
                .when().delete("/produtos/" + id)
                .then()
                .statusCode(204);

        // verificando se o produto foi excluido
        ProdutoResponseDTO produtoresponse = null;
        try {
            produtoresponse = produtoservice.findById(id);
        } catch (Exception e) {
            assert true;
        } finally {
            assertNull(produtoresponse);
        }

    }
}