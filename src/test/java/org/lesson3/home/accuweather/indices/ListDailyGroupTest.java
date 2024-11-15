package org.lesson3.home.accuweather.indices;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson3.home.accuweather.AbstractAccuweatherTest;
import org.lesson3.home.accuweather.Indices.metadata.Metadata;

import java.util.List;
import static io.restassured.RestAssured.given;

public class ListDailyGroupTest extends AbstractAccuweatherTest {

    @Test
    void getListDailyGroupIndices() {

        List<Metadata> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/groups")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", Metadata.class);

        Assertions.assertEquals(47,response.size());
        Assertions.assertEquals("All API", response.get(0).getName());
    }
}
