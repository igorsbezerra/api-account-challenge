package dev.igor.apiaccount.client;

import dev.igor.apiaccount.config.UserFeignClientConfig;
import dev.igor.apiaccount.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user", url = "http://localhost:8080", configuration = UserFeignClientConfig.class)
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{document}")
    UserDTO findByDocument(@PathVariable("document") String document);
}
