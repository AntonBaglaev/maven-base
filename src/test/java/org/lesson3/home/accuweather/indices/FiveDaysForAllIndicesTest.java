package org.lesson3.home.accuweather.indices;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson3.home.accuweather.AbstractAccuweatherTest;
import org.lesson3.home.accuweather.Indices.fiveDay.FiveDay;

import java.util.List;
import static io.restassured.RestAssured.given;

public class FiveDaysForAllIndicesTest extends AbstractAccuweatherTest {

    @Test
    void getForFiveDaysForAllIndex() {

        List<FiveDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/5day/52")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", FiveDay.class);

        Assertions.assertEquals(240,response.size());
        Assertions.assertEquals("Flight Delays", response.get(0).getName());
    }
}
