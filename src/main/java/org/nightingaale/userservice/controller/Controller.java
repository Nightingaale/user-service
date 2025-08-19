package org.nightingaale.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.userservice.event.UserRegistrationEvent;
import org.nightingaale.userservice.event.UserRemoveEvent;
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

    @PostMapping
    public ResponseEntity<?> createProfileFromEvent(@RequestBody UserRegistrationEvent event, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        event.setUserId(userId.toString());
        userService.createProfile(event);
        return ResponseEntity.ok("[User has successfully been created]");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(@RequestBody UserRemoveEvent event, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        event.setUserId(userId.toString());
        userService.deleteProfile(event);
        return ResponseEntity.ok("[User has been successfully deleted]");
    }
}