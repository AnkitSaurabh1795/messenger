package org.example.dao;

import org.example.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserDao {
    void persist(UserEntity user);

    List<UserEntity> fetch(Integer pageNumber, Integer pageSize);

    Optional<UserEntity> fetchByUserName(String userName);

    void updateById(String id, UserEntity user);

    Optional<UserEntity> fetchByUserId(String userId);
}
