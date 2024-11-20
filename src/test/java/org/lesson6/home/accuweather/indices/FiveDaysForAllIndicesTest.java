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

public class FiveDaysForAllIndicesTest extends AbstractAccuweatherTest {

    @Test
    @DisplayName("FiveDaysForAllIndicesTest")
    @Description("GET 5 Days of Daily Index Values for All Indices")
    @Severity(SeverityLevel.NORMAL)
    @Story(value = "Request testing By By Location key 52")
    void getForFiveDaysForAllIndex() {

        List<FiveDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/5day/52")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", FiveDay.class);

        Assertions.assertEquals(240,response.size());
        Assertions.assertEquals("Flight Delays", response.get(0).getName());
    }
}
