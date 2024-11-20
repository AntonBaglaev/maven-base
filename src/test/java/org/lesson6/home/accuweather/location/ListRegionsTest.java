package org.lesson6.home.accuweather.location;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lesson6.home.accuweather.AbstractAccuweatherTest;
import org.lesson6.home.accuweather.locations.list.Regions;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ListRegionsTest extends AbstractAccuweatherTest {

    @Test
    @DisplayName("ListRegionsTest")
    @Description("GET Admin Area List")
    @Severity(SeverityLevel.NORMAL)
    void getListRegions() {

        List<Regions> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/regions")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000l))
                .extract()
                .body().jsonPath().getList(".", Regions.class);

        Assertions.assertEquals(10,response.size());
        Assertions.assertEquals("Africa", response.get(0).getLocalizedName());
    }
}