package autoTest.accuweather.location;

import autoTest.accuweather.AbstractAccuweatherTest;
import autoTest.accuweather.locations.list.AdminArea;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;


public class ListAdminAreaTest extends AbstractAccuweatherTest {


    @Test
    void getListAdminArea() {

        List<AdminArea> response = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl()+"/locations/v1/adminareas/MEA")
                .then()
                .statusCode(200)
                .time(Matchers.lessThan(2000L))
                .extract()
                .body().jsonPath().getList(".", AdminArea.class);

        Assertions.assertEquals(21,response.size());
        Assertions.assertEquals("Andrijevica", response.get(0).getLocalizedName());
    }
}
