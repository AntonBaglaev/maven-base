package org.lesson6.seminar;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TenDaysTest extends AccuweatherAbstractTest {

    @Test
    void getTenDays_shouldReturn401() {
        given()
                .queryParam("apikey", getApiKey())
                .pathParam("version", "1")
                .pathParam("locationKey", 290396)
                .when()
                .get(getBaseUrl()+"/forecasts/v1/daily/10day/290396")
                .then()
                .statusCode(401);
    }
}
