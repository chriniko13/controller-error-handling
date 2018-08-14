package com.chriniko.controllererrorhandling.it;

import com.chriniko.controllererrorhandling.domain.Status;
import com.chriniko.controllererrorhandling.it.infrastructure.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HttpMessageNotReadableIT extends AbstractIntegrationTest {

    @Test
    public void not_correct_request_payload_case() {

        // GIVEN
        String malformedPayload = readResourceToString("requests/malformedPayload1.json");

        // WHEN
        try {
            doRequest(malformedPayload);
        } catch (HttpClientErrorException error) {

            // THEN
            assertNotNull(error);
            assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
            assertEquals(
                    "{\"payloadErrorPath\":\"[description]\",\"requiredTargetType\":\"String\",\"errorMessage\":\"Cannot deserialize instance of `java.lang.String` out of START_ARRAY token\"}",
                    error.getResponseBodyAsString()
            );

        }

    }

    @Test
    public void not_correct_request_payload_case_2() {

        // GIVEN
        String malformedPayload = readResourceToString("requests/malformedPayload2.json");

        // WHEN
        try {
            doRequest(malformedPayload);
        } catch (HttpClientErrorException error) {

            // THEN
            assertNotNull(error);
            assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
            assertEquals(
                    "{\"payloadErrorPath\":\"[motorcycle]\",\"requiredTargetType\":\"Motorcycle\",\"errorMessage\":\"Cannot deserialize instance of `com.chriniko.controllererrorhandling.domain.Motorcycle` out of START_ARRAY token\"}",
                    error.getResponseBodyAsString()
            );

        }

    }

    // ---- START: util functions ----
    private ResponseEntity<Status> doRequest(String adRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("sdk_api_v", "1.0.0");

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
