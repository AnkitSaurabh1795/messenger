package org.example.dao;

import org.example.entity.UserEntity;

import java.util.List;

public interface IUserDao {
    void persist(UserEntity user, String messageId);

    List<UserEntity> fetch(Integer pageNumber, Integer pageSize);
}
