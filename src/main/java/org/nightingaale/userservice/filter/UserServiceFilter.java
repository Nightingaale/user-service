package org.nightingaale.userservice.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.userservice.exception.DuplicateFieldException;
import org.nightingaale.userservice.model.dto.UserDataDto;
import org.nightingaale.userservice.repository.UserDataRepository;
import org.nightingaale.userservice.repository.UserProfileRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceFilter {

    private final UserDataRepository userDataRepository;
    private final UserProfileRepository userProfileRepository;

    public void userValidation(UserDataDto dataDto) {
        String userId = dataDto.getUserId();

        if (dataDto.getUsername() != null) {

            boolean existsUsernameInUserData = userDataRepository.existsByUsername(dataDto.getUsername(), userId);
            boolean existsUsernameInUserProfile = userProfileRepository.existsByUsername(dataDto.getUsername(), userId);

            if (existsUsernameInUserData || existsUsernameInUserProfile) {
                log.warn("[Trying to update username for userId: {}, {}]", dataDto.getUsername(), dataDto.getUserId());
                throw new DuplicateFieldException("[Error: This username is already in use]");
            }
        }

        if (dataDto.getEmail() != null) {

            boolean existsEmailInUserData = userDataRepository.existsByEmail(dataDto.getEmail(), userId);

            if (existsEmailInUserData) {
                log.warn("Trying to update email for userId: {}, {}", dataDto.getEmail(), dataDto.getUserId());
                throw new DuplicateFieldException("Error: [This email is already in use]");
            }
        }
    }
}
