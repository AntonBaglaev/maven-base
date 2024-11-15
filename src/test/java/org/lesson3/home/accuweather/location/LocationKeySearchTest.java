package autoTest.accuweather.location;

import autoTest.accuweather.AbstractAccuweatherTest;
import autoTest.accuweather.locations.locationKey.LocationKey;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class LocationKeySearchTest extends AbstractAccuweatherTest {

    @Test
    void getLocationKeySearch() {

        LocationKey response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/620")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .response()
                .body().as(LocationKey.class);
        Assertions.assertEquals("Porcheville", response.getLocalizedName());
    }
}
