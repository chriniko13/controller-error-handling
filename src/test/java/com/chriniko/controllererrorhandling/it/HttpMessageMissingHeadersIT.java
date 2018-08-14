package com.chriniko.controllererrorhandling.it;

import com.chriniko.controllererrorhandling.domain.Status;
import com.chriniko.controllererrorhandling.it.infrastructure.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HttpMessageMissingHeadersIT extends AbstractIntegrationTest {

    @Test
    public void missing_required_header_case() {

        // GIVEN
        String correctPayload = readResourceToString("requests/correctPayload.json");

        // WHEN
        try {
            doRequest(correctPayload);
        } catch (HttpClientErrorException error) {

            // THEN
            assertNotNull(error);
            assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
            assertEquals(
                    "{\"errorMessage\":\"Missing request header 'sdk_api_v' for method parameter of type String\"}",
                    error.getResponseBodyAsString()
            );

        }

    }

    // ---- START: util functions ----
    private ResponseEntity<Status> doRequest(String adRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity<String> httpEntity = new HttpEntity<>(adRequest, httpHeaders);

        return restTemplate.exchange(
                "http://localhost:" + apiPort + "/services",
                HttpMethod.POST,
                httpEntity,
                Status.class
        );
    }
    // ---- END: util functions ----
}
