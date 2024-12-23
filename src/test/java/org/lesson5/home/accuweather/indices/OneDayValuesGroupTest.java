package org.lesson5.home.accuweather.indices;

import org.lesson5.home.accuweather.AbstractTest;
import org.lesson5.home.accuweather.Indices.oneDay.OneDay;
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

public class OneDayValuesGroupTest extends AbstractTest {

    private static final Logger logger
            = LoggerFactory.getLogger(OneDayValuesGroupTest.class);

    @Test
    void getOneDayValuesGroup_shouldReturn200() throws IOException {
        logger.info("Тест код ответ 200 запущен");
        ObjectMapper mapper = new ObjectMapper();
        OneDay oneDay = new OneDay();
        oneDay.setName("Fishing Forecast");

        logger.debug("Формируем мок GET /indices/v1/daily/1day/5/groups/8");
        stubFor(get(urlPathEqualTo("/indices/v1/daily/1day/5/groups/8"))
                .willReturn(aResponse().withStatus(200)
                        .withBody(mapper.writeValueAsString(oneDay))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http-клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + "/indices/v1/daily/1day/5/groups/8");

        HttpResponse response = httpClient.execute(request);

        verify(getRequestedFor(urlPathEqualTo("/indices/v1/daily/1day/5/groups/8")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("Fishing Forecast",
                mapper.readValue(response.getEntity().getContent(), OneDay.class).getName());
    }


    @Test
    void getOneDayValuesGroup_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 401 запущен");
        //given
        logger.debug("Формируем мок GET /indices/v1/daily/1day/5/groups/8");
        stubFor(get(urlPathEqualTo("/indices/v1/daily/1day/5/groups/8"))
                .withQueryParam("apiKey", containing("4mntXiAzkJI6JWapQAWQHhWVUD7VEI1E"))
                .willReturn(aResponse()
                        .withStatus(401).withBody("Unauthorized")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/indices/v1/daily/1day/5/groups/8");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "P_4mntXiAzkJI6JWapQAWQHhWVUD7VEI1E")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/indices/v1/daily/1day/5/groups/8")));
        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("Unauthorized", convertResponseToString(response));
    }
}
