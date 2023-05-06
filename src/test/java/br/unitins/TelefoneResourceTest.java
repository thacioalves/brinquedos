package br.unitins;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import br.unitins.dto.telefone.TelefoneDTO;
import br.unitins.dto.telefone.TelefoneResponseDTO;
import br.unitins.service.telefone.TelefoneService;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.inject.Inject;

@QuarkusTest
public class TelefoneResourceTest {

    @Inject
    TelefoneService telefoneservice;

    @Test
    public void testGetAll() {
        given()
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
}