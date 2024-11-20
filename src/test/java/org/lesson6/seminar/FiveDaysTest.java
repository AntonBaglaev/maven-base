package org.lesson6.seminar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson3.seminar.weather.Weather;

import static io.restassured.RestAssured.given;

public class FiveDaysTest extends AccuweatherAbstractTest{

    @Test
    void test5Days() {
        Weather weather = given().queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/5day/290396")
                .then()
                .statusCode(200)
                .extract()
                .body().as(Weather.class);

        Assertions.assertEquals(5, weather.getDailyForecasts().size());
    }
}
