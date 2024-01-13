package dev.igor.apiaccount.config;

import dev.igor.apiaccount.error.handler.UserFeignError;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class UserFeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserFeignError();
    }
}
