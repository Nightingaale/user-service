package org.nightingaale.userservice.client;

import org.nightingaale.userservice.config.FeignClientConfig;
import org.nightingaale.userservice.event.KafkaUserUpdateRequestEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", configuration = FeignClientConfig.class)
public interface AuthServiceClient {
    @PatchMapping("/api/v1/auth")
    void updateUser(@RequestBody KafkaUserUpdateRequestEvent request);
}
