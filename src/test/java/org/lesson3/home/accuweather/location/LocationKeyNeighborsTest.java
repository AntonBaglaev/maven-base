package autoTest.accuweather.location;

import autoTest.accuweather.AbstractAccuweatherTest;
import autoTest.accuweather.locations.locationKey.LocationKey;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class LocationKeyNeighborsTest extends AbstractAccuweatherTest {

    @Test
    void getLocationKeyNeighbors() {
        List<LocationKey> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/neighbors/620")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", LocationKey.class);

        Assertions.assertEquals(10,response.size());
        Assertions.assertEquals("Mantes-la-Jolie", response.get(0).getLocalizedName());
    }
}
