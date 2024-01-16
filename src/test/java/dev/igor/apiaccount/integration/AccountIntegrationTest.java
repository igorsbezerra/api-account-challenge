package dev.igor.apiaccount.integration;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
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

import dev.igor.apiaccount.RabbitMQContainer;
import dev.igor.apiaccount.client.UserClient;
import dev.igor.apiaccount.dto.UserDTO;
import dev.igor.apiaccount.repository.AccountRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith({RabbitMQContainer.class})
@TestInstance(Lifecycle.PER_CLASS)
public class AccountIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private AccountRepository repository;
    @Autowired UserClient userClient;
    private ClientAndServer mockServer;

    @BeforeAll
    private void start() {
        mockServer = ClientAndServer.startClientAndServer(8089);
    }

    @BeforeEach
    private void reset() {
        mockServer.reset();
    }

    @AfterAll
    private void stop() {
        mockServer.stop();
    }
    
    @Test
    void it_must_be_possible_to_create_a_user_when_executing_post_endpoint() throws Exception {
        String userJson = createUserJson();
        mockServer.when(
            HttpRequest.request().withMethod("GET").withPathParameter("document", "34686598650")
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(200).withBody(userJson)
        );

        String json = createJson();

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(json)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void it_should_not_be_possible_to_create_a_user_when_executing_endpoint_post() throws JsonProcessingException, Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createInvalidJson())
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void it_should_not_be_possible_to_create_a_user_when_executing_endpoint_post_user_not_found() throws JsonProcessingException, Exception {
        mockServer.when(
            HttpRequest.request().withMethod("GET").withPathParameter("document", "34686598650")
        ).respond(
            HttpResponse.response().withContentType(org.mockserver.model.MediaType.APPLICATION_JSON).withStatusCode(404)
        );
        
        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createInvalidJson())
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void test3() {}

    private String createJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("document", "34686598650");
        objectNode.put("agency", "Santander - 090");
        return mapper.writeValueAsString(objectNode);
    }

    private String createInvalidJson() throws JsonProcessingException {
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("document", "34");
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
