package dev.igor.apiaccount.integration;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import dev.igor.apiaccount.MockServerAPIContainer;
import dev.igor.apiaccount.RabbitMQContainer;
import dev.igor.apiaccount.dto.UserDTO;
import dev.igor.apiaccount.model.Account;
import dev.igor.apiaccount.repository.AccountRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith({ RabbitMQContainer.class, MockServerAPIContainer.class })
public class AccountIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private AccountRepository repository;
    
    @Test
    void it_must_be_possible_to_create_a_user_when_executing_post_endpoint() throws Exception {
        String userJson = createUserJson();
        MockServerAPIContainer.mockServerClient.when(
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
        MockServerAPIContainer.mockServerClient.when(
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
    void it_should_be_possible_to_find_account_when_executing_endpoint_get() throws JsonProcessingException, Exception {
        Account account = repository.save(Account.create("12345", "santander", "1"));
        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/" + account.getAccountCode())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createInvalidJson())
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void it_not_should_be_possible_to_find_account_when_executing_endpoint_get_account_not_found() throws JsonProcessingException, Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/not_found")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(createInvalidJson())
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

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
