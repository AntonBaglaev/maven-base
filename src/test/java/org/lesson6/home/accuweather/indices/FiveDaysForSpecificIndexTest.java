package org.lesson6.home.accuweather.indices;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lesson6.home.accuweather.AbstractAccuweatherTest;
import org.lesson6.home.accuweather.Indices.fiveDay.FiveDay;

import java.util.List;

import static io.restassured.RestAssured.given;

public class FiveDaysForSpecificIndexTest extends AbstractAccuweatherTest {

    @Test
    @DisplayName("FiveDaysForSpecificIndexTest")
    @Description("GET 5 Days of Daily Index Values for a Specific Index")
    @Severity(SeverityLevel.NORMAL)
    @Story(value = "Request testing By ID 52, By Index Group ID 8")
    void getForFiveDaysForASpecificIndex() {

        List<FiveDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/5day/52/8")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", FiveDay.class);

        Assertions.assertEquals(5,response.size());
        Assertions.assertEquals("Outdoor Concert Forecast", response.get(0).getName());
    }
}
