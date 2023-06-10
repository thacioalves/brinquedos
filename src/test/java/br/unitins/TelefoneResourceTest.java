package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import br.unitins.dto.AuthUsuarioDTO;
import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;
import br.unitins.service.telefone.TelefoneService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;

import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneResourceTest {

        @Inject
        TelefoneService telefoneservice;

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
                                .when().get("/telefones")
                                .then()
                                .statusCode(200);
        }

        @Test
        public void testInsert() {
                TelefoneDTO telefone = new TelefoneDTO(
                                "63", "992706485");

                TelefoneResponseDTO telefonecreate = telefoneservice.create(telefone);
                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(telefonecreate)
                                .when().post("/telefones")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "codigoArea", is("63"), "numero", is("992706485"));
        }

        @Test
        public void testUpdate() {
                // Adicionando um telefone no banco de dados
                TelefoneDTO telefone = new TelefoneDTO(
                                "63", "992706485");
                Long id = telefoneservice.create(telefone).id();

                // Criando outro telefone para atuailzacao
                TelefoneDTO telefoneupdate = new TelefoneDTO(
                                "62", "984706485");

                TelefoneResponseDTO telefoneatualizado = telefoneservice.update(id, telefoneupdate);

                given()
                                .header("Authorization", "Bearer " + token)
                                .contentType(ContentType.JSON)
                                .body(telefoneatualizado)
                                .when().put("/telefones/" + id)
                                .then()
                                .statusCode(204);

                // Verificando se os dados foram atualizados no banco de dados
                TelefoneResponseDTO telefoneresponse = telefoneservice.findById(id);
                assertThat(telefoneresponse.codigoArea(), is("62"));
                assertThat(telefoneresponse.numero(), is("984706485"));
        }

        @Test
        public void testDelete() {
                // Adicionando um telefone no banco de dados
                TelefoneDTO telefone = new TelefoneDTO(
                                "63", "992706485");
                Long id = telefoneservice.create(telefone).id();
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().delete("/telefones/" + id)
                                .then()
                                .statusCode(204);

                // verificando se o telefone foi excluida
                TelefoneResponseDTO telefoneresponse = null;
                try {
                        telefoneresponse = telefoneservice.findById(id);
                } catch (Exception e) {
                        assert true;
                } finally {
                        assertNull(telefoneresponse);
                }

        }

        @Test
        public void testFindById() {
                TelefoneDTO telefone = new TelefoneDTO(
                                "63", "992706485");
                Long id = telefoneservice.create(telefone).id();
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/telefones/" + id)
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testCount() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/telefones/count")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }

        @Test
        public void testSearch() {
                given()
                                .header("Authorization", "Bearer " + token)
                                .when().get("/telefones/search/nome")
                                .then()
                                .statusCode(200)
                                .body(notNullValue());
        }
}