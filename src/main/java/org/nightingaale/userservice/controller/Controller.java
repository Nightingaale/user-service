package org.nightingaale.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.userservice.dto.UserDto;
import org.nightingaale.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class Controller {

    private final UserService userService;

    @PostMapping("/Info")
    public ResponseEntity<?> createProfile(@RequestBody UserDto event) {
        event.setId(UUID.randomUUID().toString());
        userService.createProfile(event);
        return ResponseEntity.ok("User's profile information has successfully been created");
    }
}
