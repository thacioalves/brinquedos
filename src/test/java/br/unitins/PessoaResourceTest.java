package br.unitins;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.pessoa.PessoaDTO;
import br.unitins.dto.pessoa.PessoaResponseDTO;
import br.unitins.service.pessoa.PessoaService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

@QuarkusTest
public class PessoaResourceTest {

        @Inject
        PessoaService pessoaService;

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
                                .when().get("/pessoas")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                PessoaDTO pessoa = new PessoaDTO("aleatoria",
                                "111.111.111-11",
                                "teste@gmail.com",
                                1);

                PessoaResponseDTO pessoaCreate = pessoaService.create(pessoa);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(pessoaCreate)
                                .when().post("/pessoas")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(),
                                                "nome", is("aleatoria"),
                                                "cpf", is("111.111.111-11"),
                                                "email", is("teste@gmail.com"),
                                                "sexo", is(1));
        }

        @Test
        public void testUpdate() {
                // Adicionando uma pessoa no banco de dados
                PessoaDTO pessoa = new PessoaDTO("aleatoria",
                                "111.111.111-11",
                                "teste@gmail.com",
                                1);
                Long id = pessoaService.create(pessoa).id();

                // Criando outra pessoa para atuailzacao
                PessoaDTO pessoaUpdate = new PessoaDTO("teste1",
                                "111.111.111-12",
                                "teste1@gmail.com",
                                2);

                PessoaResponseDTO pessoaAtualizada = pessoaService.update(id, pessoaUpdate);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(pessoaAtualizada)
                                .when().put("/pessoas/" + id)
                                .then()
                                .statusCode(204);

                // Verificando se os dados foram atualizados no banco de dados
                PessoaResponseDTO pessoaResponse = pessoaService.findById(id);
                assertThat(pessoaResponse.nome(), is("teste1"));
                assertThat(pessoaResponse.cpf(), is("111.111.111-12"));
                assertThat(pessoaResponse.email(), is("teste1@gmail.com"));
                assertThat(pessoaResponse.sexo(), is(2));
        }

        @Test
        public void testDelete() {
                // Adicionando uma pessoa no banco de dados
                PessoaDTO pessoa = new PessoaDTO("teste",
                                "111.111.111-11",
                                "teste@gmail.com",
                                1);
                Long id = pessoaService.create(pessoa).id();
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().delete("/pessoas/" + id)
                                .then()
                                .statusCode(204);

                // verificando se a pessoa foi excluida
                PessoaResponseDTO pessoaResponse = null;
                try {
                        pessoaResponse = pessoaService.findById(id);
                } catch (Exception e) {
                        assert true;
                } finally {
                        assertNull(pessoaResponse);
                }

        }

        @Test
        public void testFindById() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/pessoas/1")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testCount() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/pessoas/count")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testSearch() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/pessoas/search/nome")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

}
