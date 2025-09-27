package org.nightingaale.userservice.repository;

import org.nightingaale.userservice.model.entity.UserProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfileEntity, String> {
    void deleteByUserId(String userId);
    boolean existsByUsername(String username, String userId);
}
