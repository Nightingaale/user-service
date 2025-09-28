package org.nightingaale.userservice.client;

import org.nightingaale.userservice.config.FeignClientConfig;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service", path = "/api/v1/auth", configuration = FeignClientConfig.class)
public interface AuthServiceClient {
    @RequestMapping(method = RequestMethod.PATCH)
    void updateUser(@RequestBody KafkaUserUpdateRequestEvent request);
}
