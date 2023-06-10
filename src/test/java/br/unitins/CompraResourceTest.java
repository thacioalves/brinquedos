package br.unitins;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import br.unitins.service.compra.CompraService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CompraResourceTest {
    
    @Inject
    CompraService compraService;

    @Test
    public void testGetAll() {
        given()
                .when().get("/compras")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsert() {
        
        
    }
}
