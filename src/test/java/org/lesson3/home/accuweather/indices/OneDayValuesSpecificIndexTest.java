package org.lesson3.home.accuweather.indices;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson3.home.accuweather.AbstractAccuweatherTest;
import org.lesson3.home.accuweather.Indices.oneDay.OneDay;

import java.util.List;
import static io.restassured.RestAssured.given;

public class OneDayValuesSpecificIndexTest extends AbstractAccuweatherTest {

    @Test
    void getOneDayValuesGroup() {

        List<OneDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/1day/5/8")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", OneDay.class);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Outdoor Concert Forecast", response.get(0).getName());
    }
}
