package org.nightingaale.userservice.repository;

import org.nightingaale.userservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    List<UserEntity> findByUserId(String userId);
}
