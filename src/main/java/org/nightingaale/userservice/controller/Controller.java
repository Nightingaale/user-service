package org.nightingaale.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.userservice.event.consumer.KafkaUserRegistrationEvent;
import org.nightingaale.userservice.event.consumer.KafkaUserRemoveEvent;
import org.nightingaale.userservice.model.dto.UserDataDto;
import org.nightingaale.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;

    @PostMapping("/registered")
    public ResponseEntity<?> createProfileFromEvent(@RequestBody KafkaUserRegistrationEvent event, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        event.setUserId(userId.toString());
        userService.createProfile(event);
        return ResponseEntity.ok("[User has successfully been created]");
    }

    @DeleteMapping("/removed")
    public ResponseEntity<?> deleteProfile(@RequestBody KafkaUserRemoveEvent event, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        event.setUserId(userId.toString());
        userService.deleteProfile(event);
        return ResponseEntity.ok("[User has been successfully deleted]");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal Jwt jwt) {
        return userService.getProfileById(jwt.getSubject())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDataDto dataDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        dataDto.setUserId(userId.toString());
        userService.requestToUpdate(dataDto);
        return ResponseEntity.ok("[User has successfully updated]");
    }
}