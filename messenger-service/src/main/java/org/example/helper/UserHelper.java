package org.example.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserHelper {

    private final IUserDao userDao;


    public Boolean isUserLoggedIn(String userName) {
        Optional<UserEntity> userEntity = userDao.fetchByUserName(userName);

        return userEntity.isPresent() && Objects.nonNull(userEntity.get().getToken()) && userEntity.get().getToken().equals("default_token");
    }


    public String getUserID(String userName) {

        return userDao.fetchByUserName(userName).orElseThrow(() ->
                new RuntimeException("User id not found for user " + userName)).getUserId();

    }

    public String getUserName(String userId) {
        return userDao.fetchByUserId(userId).orElseThrow(() ->
                new RuntimeException("User name not found for user " + userId)).getUserName();
    }
}
