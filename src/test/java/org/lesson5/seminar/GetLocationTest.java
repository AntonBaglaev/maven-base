package org.lesson5.seminar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lesson5.seminar.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GetLocationTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetLocationTest.class);

    @Test
    void test401Error() throws URISyntaxException, IOException {
        logger.info("Тест 401 запущен");
        //given
        logger.debug("Формирование мока для сервиса GetLocation");

        stubFor(WireMock.get(urlPathEqualTo("/location/v1/cities/autocomplete"))
                .withQueryParam("apikey", notMatching("10000"))
                .willReturn(aResponse().withStatus(401).withBody("Error")));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(getBaseUrl()+"/location/v1/cities/autocomplete");
        URI uri = new URIBuilder(get.getURI())
                .addParameter("apikey", "20000")
                .build();
        get.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = client.execute(get);
        //then
        verify(getRequestedFor(urlPathEqualTo("/location/v1/cities/autocomplete")));
        Assertions.assertEquals(401, response.getStatusLine().getStatusCode());
    }

    @Test
    void test200ResponseCode() throws IOException, URISyntaxException {
        logger.info("Тест 200 запущен");
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");
        Location bodyError = new Location();
        bodyError.setKey("Error");
        stubFor(WireMock.get(urlPathEqualTo("/location/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Samara"))
                .willReturn(aResponse().withStatus(200).withBody(objectMapper.writeValueAsString(bodyOk))));
        stubFor(WireMock.get(urlPathEqualTo("/location/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("Error"))
                .willReturn(aResponse().withStatus(200).withBody(objectMapper.writeValueAsString(bodyError))));
        logger.debug("Мокирование для теста test200ResponseCode завершено");
        //when
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(getBaseUrl()+"/location/v1/cities/autocomplete");

        URI uri = new URIBuilder(get.getURI())
                .addParameter("q", "Samara")
                .build();
        get.setURI(uri);

        HttpResponse responseOk = client.execute(get);

        URI uriError = new URIBuilder(get.getURI())
                .addParameter("q", "Samara")
                .build();
        get.setURI(uriError);

        HttpResponse responseError = client.execute(get);

        //then
        verify(2, getRequestedFor(urlPathEqualTo("/location/v1/cities/autocomplete")));
        Assertions.assertEquals(200, responseOk.getStatusLine().getStatusCode());
        Assertions.assertEquals(200, responseError.getStatusLine().getStatusCode());

        Location LocationOk = objectMapper.readValue(responseOk.getEntity().getContent(), Location.class);
        Location LocationError = objectMapper.readValue(responseError.getEntity().getContent(), Location.class);

        Assertions.assertEquals("OK", LocationOk.getKey());
        Assertions.assertEquals("Error", LocationError.getKey());
    }
}
