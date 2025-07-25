package org.nightingaale.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.dto.UserDto;
import org.nightingaale.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final UserService userService;

    @PostMapping("/createInfo")
    public ResponseEntity<?> createProfile(@RequestBody UserDto userDto, @AuthenticationPrincipal Jwt jwt) {
        try {

            UUID userId = UUID.fromString(jwt.getSubject());
            userDto.setId(userId.toString());

        } catch (RuntimeException e) {
            log.error("[Invalid JWT subject: " + e + "]");
            throw new RuntimeException("[Invalid userId in JWT]");
        }
        userService.createProfile(userDto);
        return ResponseEntity.ok("[User's profile information has successfully been created]");
    }
}
