package dev.igor.apiaccount.error.handler;

import dev.igor.apiaccount.error.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class UserFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is4xxClientError()) {
            throw new UserNotFoundException();
        }
        return null;
    }
}
