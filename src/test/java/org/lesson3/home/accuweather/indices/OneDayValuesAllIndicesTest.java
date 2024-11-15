package org.lesson3.home.accuweather.indices;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson3.home.accuweather.AbstractAccuweatherTest;
import org.lesson3.home.accuweather.Indices.oneDay.OneDay;

import java.util.List;
import static io.restassured.RestAssured.given;

public class OneDayValuesAllIndicesTest extends AbstractAccuweatherTest {

    @Test
    void getOneDayValuesGroup() {

        List<OneDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/1day/5")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", OneDay.class);

        Assertions.assertEquals(48,response.size());
        Assertions.assertEquals("Flight Delays", response.get(0).getName());
    }
}
