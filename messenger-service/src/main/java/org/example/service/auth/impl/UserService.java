package org.example.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.example.helper.UserHelper;
import org.example.model.request.UserCreateRequest;
import org.example.model.request.UserLoginRequest;
import org.example.model.response.AllUserResponse;
import org.example.service.auth.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.util.Util.generateUserId;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final UserHelper userHelper;
    private final IUserDao userDao;
    @Override
    public String createUser(UserCreateRequest user) {

        if(user.getUserName() != null && user.getPassCode() != null && user.getUserName() != "" && user.getPassCode() != "") {
            Optional<UserEntity> userEntity = userDao.fetchByUserName(user.getUserName());

            if(userEntity.isPresent()) {
                log.info("User {} already exists ", user.getUserName());
                return "User already exists";
            }

            userDao.persist(UserEntity.builder().userId(generateUserId()).userName(user.getUserName())
                    .password(user.getPassCode()).role("USER").build());
            log.info("User {} created Successfully ", user.getUserName());
            return "success";
        }
        else {
            log.info("UserInfo:{}","Username or password can not be empty");
            return "Username or password can not be empty";
        }
    }

    @Override
    public String loginUser(UserLoginRequest user) {
        Optional<UserEntity> userEntity = userDao.fetchByUserName(user.getUserName());
        UserEntity info = userEntity.orElse(null);
        if (!userHelper.isUserLoggedIn(user.getUserName())) {
            if(info.getPassword().equals(user.getPassCode())) {
                final String token = "default_token";

                info.setToken(token);
                userDao.updateById(info.getUserId(), info);
                log.info("User {} successfully logged in", user.getUserName());
                return "success";
            }
            return "wrong password";

        }
        log.info("User {} is already logged in", user.getUserName());
        return "user is already logged in";

    }

    @Override
    public String logoutUser(String userName) {
        //Optional<UserEntity> userEntity = userDao.fetchByUserName(user.getUserName());
        Optional<UserEntity> userEntity = userDao.fetchByUserName(userName);
        UserEntity info = userEntity.orElse(null);
        log.info("UserInfo:{}",info);
        if(info == null) {
            return "User does not exist";
        }
        else if (info != null && info.getToken() != null) {
            final String token = "expire_token";
            info.setToken(token);
            userDao.updateById(info.getUserId(), info);
            log.info("User {} successfully logged out", userName);
            return "success";
        }
        log.info("User {} is already logged out", userName);
        return "User is already logged out";
    }

    @Override
    public AllUserResponse fetchAllUsers() {
        List<UserEntity> userEntities = userDao.fetch(0, 50);
        if(userEntities.isEmpty()) {
            return AllUserResponse.builder().status("No user exist").build();
        }
        AllUserResponse response = AllUserResponse.builder().status("success").data(new ArrayList<>()).build();
        userEntities.forEach(user -> {
            response.getData().add(user.getUserName());
        });
        return response;
    }

}
