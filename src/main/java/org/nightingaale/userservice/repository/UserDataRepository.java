package org.nightingaale.userservice.repository;

import org.nightingaale.userservice.model.entity.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, String> {
    void deleteByUserId(String userId);
    boolean existsByUsernameAndUserIdNot(String username, String userId);
    boolean existsByEmailAndUserIdNot(String email, String userId);
}
