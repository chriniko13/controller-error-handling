package com.chriniko.controllererrorhandling.it;

import com.chriniko.controllererrorhandling.domain.Status;
import com.chriniko.controllererrorhandling.it.infrastructure.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.http.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CorrectPayloadIT extends AbstractIntegrationTest {

    @Test
    public void not_correct_request_payload_case() {

        // GIVEN
        String fullCorrectPayload = readResourceToString("requests/fullCorrectPayload.json");

        // WHEN
        ResponseEntity<Status> result = doRequest(fullCorrectPayload);

        // THEN
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());

        Status body = result.getBody();
        assertEquals(new Status("service saved successfully!"), body);

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
