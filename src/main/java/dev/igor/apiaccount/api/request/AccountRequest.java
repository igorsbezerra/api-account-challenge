package dev.igor.apiaccount.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    @Size(min = 11, max = 14)
    @NotEmpty
    private String document;
    @Size(max = 50)
    @NotEmpty
    private String agency;
}
