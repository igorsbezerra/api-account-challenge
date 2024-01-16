package dev.igor.apiaccount.integration;

import java.util.UUID;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import dev.igor.apiaccount.RabbitMQContainer;
import dev.igor.apiaccount.dto.UserDTO;
import dev.igor.apiaccount.repository.AccountRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith({RabbitMQContainer.class})
public class AccountIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private AccountRepository repository;
    @Rule private WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    void test() throws Exception {

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/users"))
                .withQueryParam("document", WireMock.equalTo("document"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(createUserJson())));

        String json = createJson();

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void test2() {}

    @Test
    void test3() {}

    private String createJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("document", "34686598650");
        objectNode.put("agency", "Santander - 090");
        return mapper.writeValueAsString(objectNode);
    }

    private String createUserJson() throws JsonProcessingException {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(expectedId);
        userDTO.setName(expectedName);
        userDTO.setDocument(expectedDocument);
        return mapper.writeValueAsString(userDTO);
    }
}
