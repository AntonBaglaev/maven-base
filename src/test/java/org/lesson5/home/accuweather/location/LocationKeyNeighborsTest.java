package org.lesson5.home.accuweather.location;

import org.lesson5.home.accuweather.AbstractTest;
import org.lesson5.home.accuweather.locations.locationKey.LocationKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationKeyNeighborsTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(LocationKeyNeighborsTest.class);


    @Test
    void getLocationKeyNeighbors_shouldReturn200() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        ObjectMapper mapper = new ObjectMapper();
        LocationKey city = new LocationKey();
        city.setLocalizedName("Mantes-la-Jolie");

        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/620");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/620"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(city))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/locations/v1/cities/neighbors/620");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/620")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Mantes-la-Jolie", mapper.readValue(response
                .getEntity().getContent(), LocationKey.class).getLocalizedName());
    }


    @Test
    void getLocationKeyNeighbors_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 401 запущен");
        //given
        logger.debug("Формируем мок GET /locations/v1/cities/neighbors/620");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/neighbors/620"))
                .withQueryParam("apiKey", containing("4mntXiAzkJI6JWapQAWQHhWVUD7VEI1E"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("Unauthorized")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/neighbors/620");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "P_4mntXiAzkJI6JWapQAWQHhWVUD7VEI1E")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/neighbors/620")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(response));
    }
}
