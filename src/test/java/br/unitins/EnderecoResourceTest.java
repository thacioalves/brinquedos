package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.cidade.CidadeDTO;
import br.unitins.dto.endereco.EnderecoDTO;
import br.unitins.dto.endereco.EnderecoResponseDTO;
import br.unitins.dto.estado.EstadoDTO;
import br.unitins.model.Cidade;
import br.unitins.service.cidade.CidadeService;
import br.unitins.service.endereco.EnderecoService;
import br.unitins.service.estado.EstadoService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class EnderecoResourceTest {

        @Inject
        EnderecoService enderecoservice;

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
                                .when().get("/enderecos")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                Long id = estadoservice.create(new EstadoDTO("Tocantins", "TO")).id();

                Long idCidade = cidadeservice.create(new CidadeDTO("Palmas", id)).id();
                EnderecoDTO enderecos = new EnderecoDTO(
                                "604 sul", "Plano Diretor", "alameda A", "QI 17 LT 30", "12345678", idCidade);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecos)
                                .when().post("/enderecos")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "rua", is("604 sul"), "bairro", is("Plano Diretor"),
                                                "numero", is("alameda A"), "complemento", is("QI 17 LT 30"), "cep",
                                                is("12345678"), "cidade", notNullValue(Cidade.class));
        }

        @Test
        public void testUpdate() {
                // Adicionando um endereco no banco de dados
                EnderecoDTO endereco = new EnderecoDTO(
                                "604 sul", "Plano Diretor", "alameda A", "QI 17 LT 30", "88888-864", (long) 1);
                Long id = enderecoservice.create(endereco).id();

                // Criando outro endereco para atuailzacao
                EnderecoDTO enderecoupdate = new EnderecoDTO(
                                "603 sul", "Plano Diretor", "alameda A", "QI 17 LT 30", "88888-864", (long) 1);

                EnderecoResponseDTO enderecoatualizado = enderecoservice.update(id, enderecoupdate);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(enderecoatualizado)
                                .when().put("/enderecos/" + id)
                                .then()
                                .statusCode(204);

                // Verificando se os dados foram atualizados no banco de dados
                EnderecoResponseDTO enderecoresponse = enderecoservice.findById(id);
                assertThat(enderecoresponse.rua(), is("603 sul"));
                assertThat(enderecoresponse.bairro(), is("Plano diretor"));
                assertThat(enderecoresponse.numero(), is("alameda A"));
                assertThat(enderecoresponse.complemento(), is("QI 17 LT 30"));
                assertThat(enderecoresponse.cep(), is("QI 17 LT 30"));
                assertThat(enderecoresponse.cidade(), is(1));
        }

        @Test
        public void testDelete() {
                // Adicionando um endereco no banco de dados
                EnderecoDTO endereco = new EnderecoDTO(
                                "604 sul", "Plano Diretor", "alameda A", "QI 17 LT 30", "88888-864", (long) 1);
                Long id = enderecoservice.create(endereco).id();
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().delete("/enderecos/" + id)
                                .then()
                                .statusCode(204);

                // verificando se o endereco foi excluido
                EnderecoResponseDTO enderecoresponse = null;
                try {
                        enderecoresponse = enderecoservice.findById(id);
                } catch (Exception e) {
                        assert true;
                } finally {
                        assertNull(enderecoresponse);
                }

        }

        @Test
        public void testFindById() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/id")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testCount() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/count")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testSearch() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/enderecos/search")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }
}