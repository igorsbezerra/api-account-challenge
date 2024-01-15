package dev.igor.apiaccount.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDTOTest {
    @Test
    void getter_and_setter() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedName = "name";
        final var expectedDocument = "document";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(expectedId);
        userDTO.setName(expectedName);
        userDTO.setDocument(expectedDocument);

        Assertions.assertEquals(expectedId, userDTO.getId());
        Assertions.assertEquals(expectedName, userDTO.getName());
        Assertions.assertEquals(expectedDocument, userDTO.getDocument());
    }
}
