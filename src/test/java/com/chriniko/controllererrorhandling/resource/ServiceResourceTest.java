package com.chriniko.controllererrorhandling.resource;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceResource.class)
public class ServiceResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_correct_case() throws Exception {

        String fullCorrectPayload = readResourceToString("requests/fullCorrectPayload.json");

        mockMvc
                .perform(
                        post("/services")
                                .header("Content-Type", "application/json")
                                .header("sdk_api_v", "1.0")
                                .content(fullCorrectPayload)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        content().string("{\"message\":\"service saved successfully!\"}")
                );
    }

    @Test
    public void save_malformed_payload_case() throws Exception {
        String malformedPayload = readResourceToString("requests/malformedPayload2.json");

        mockMvc
                .perform(
                        post("/services")
                                .header("Content-Type", "application/json")
                                .header("sdk_api_v", "1.0")
                                .content(malformedPayload)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        content().string("{\"payloadErrorPath\":\"[motorcycle]\",\"requiredTargetType\":\"Motorcycle\",\"errorMessage\":\"Cannot deserialize instance of `com.chriniko.controllererrorhandling.domain.Motorcycle` out of START_ARRAY token\"}")
                );
    }

    @Test
    public void save_missing_required_headers_case() throws Exception {

        String fullCorrectPayload = readResourceToString("requests/fullCorrectPayload.json");

        mockMvc
                .perform(
                        post("/services")
                                .header("Content-Type", "application/json")
                                .content(fullCorrectPayload)
                )
                .andExpect(
                        status().isBadRequest()
                )
                .andExpect(
                        content().string("{\"errorMessage\":\"Missing request header 'sdk_api_v' for method parameter of type String\"}")
                );

    }

    @SneakyThrows({IOException.class, URISyntaxException.class})
    private String readResourceToString(String name) {

        URI uri = Objects
                .requireNonNull(this.getClass().getClassLoader().getResource(name))
                .toURI();

        Path path = Paths.get(uri);

        return Files.readAllLines(path).stream().collect(Collectors.joining());
    }
}