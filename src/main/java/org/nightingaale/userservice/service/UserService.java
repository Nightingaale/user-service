package org.nightingaale.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.dto.UserDto;
import org.nightingaale.userservice.entity.UserEntity;
import org.nightingaale.userservice.mapper.UsersInfoMapper;
import org.nightingaale.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersInfoMapper usersInfoMapper;
    private final UserRepository userRepository;

    public void createProfile(UserDto userDto) {
        try {
            if (userRepository.existsById(userDto.getId())) {
                log.warn("[User with id: " + userDto.getId() + " already exists]");
                return;
            }

            UserDto event = new UserDto();
            event.setUsername(userDto.getUsername());
            event.setFirstName(userDto.getFirstName());
            event.setLastname(userDto.getLastname());
            event.setBio(userDto.getBio());

            UserEntity userEntity = usersInfoMapper.toEntity(userDto);
            userRepository.save(userEntity);
            log.info("[User's info with id: " + userDto.getId() + " created]");
        }
        catch (RuntimeException e) {
            log.error("[User's info cannot be created. Error: " + e.getMessage() + "]");
        }
    }
}