package com.chriniko.controllererrorhandling.it.infrastructure;

import com.chriniko.controllererrorhandling.ControllerErrorHandlingApplication;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)

@SpringBootTest(
        classes = ControllerErrorHandlingApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@ActiveProfiles("integration")

@Ignore
public class AbstractIntegrationTest {

    @LocalServerPort
    protected Integer apiPort;

    @Autowired
    protected RestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setUp() {
        // Note: add your prepare logic here.
    }

    // --- START: util functions ---
    @SneakyThrows({IOException.class, URISyntaxException.class})
    protected String readResourceToString(String name) {

        URI uri = Objects
                .requireNonNull(this.getClass().getClassLoader().getResource(name))
                .toURI();

        Path path = Paths.get(uri);

        return Files.readAllLines(path).stream().collect(Collectors.joining());
    }

    @SneakyThrows({JsonMappingException.class, IOException.class})
    protected <T> T deserialize(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }
    // --- END: util functions ---

}
