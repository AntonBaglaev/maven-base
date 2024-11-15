package autoTest.accuweather.location;

import autoTest.accuweather.AbstractAccuweatherTest;
import autoTest.accuweather.locations.locationKey.LocationKey;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;

public class TextSearchCityTest extends AbstractAccuweatherTest {

    @Test
    void getTextSearchCity() {

        List<LocationKey> response = given()
                .queryParam("apikey", getApiKey())
                .queryParam("q", "Missouri")
                .when()
                .get(getBaseUrl()+"/locations/v1/cities/search")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", LocationKey.class);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Missouri", response.get(0).getEnglishName());
    }
}
