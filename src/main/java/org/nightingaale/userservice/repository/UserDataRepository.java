package org.nightingaale.userservice.repository;

import org.nightingaale.userservice.model.entity.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserDataEntity, String> {
    void deleteByUserId(String userId);
    boolean existsByUsernameAndUserIdNot(String username, String userId);
    boolean existsByEmailAndUserIdNot(String email, String userId);

    @Query("SELECT u.correlationId FROM UserDataEntity u WHERE u.userId = :userId")
    Optional<String> findCorrelationIdByUserId(String userId);
}
