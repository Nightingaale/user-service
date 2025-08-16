package org.nightingaale.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.userservice.event.UserRegistrationEvent;
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
        return ResponseEntity.ok("[User's profile information has successfully been created]");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        userService.deleteProfile(userId);
        return ResponseEntity.ok("[User's profile has been deleted]");
    }
}