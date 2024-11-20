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
import org.lesson6.home.accuweather.Indices.oneDay.OneDay;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OneDayValuesGroupTest extends AbstractAccuweatherTest {

    @Test
    @DisplayName("OneDayValuesGroupTest")
    @Description("GET 1 Day of Daily Index Values for a Group of Indices")
    @Severity(SeverityLevel.NORMAL)
    @Story(value = "Request testing By Location key 5, Index Group 8")
    void getOneDayValuesGroup() {

        List<OneDay> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/indices/v1/daily/1day/5/groups/8")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", OneDay.class);

        Assertions.assertEquals(3,response.size());
        Assertions.assertEquals("Fishing Forecast", response.get(0).getName());
    }
}
