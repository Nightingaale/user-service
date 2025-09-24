package org.nightingaale.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nightingaale.userservice.model.dto.UserDataDto;
import org.nightingaale.userservice.model.dto.UserProfileDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestEvent {
    private UserDataDto userDataDto;
    private UserProfileDto userProfileDto;
}
