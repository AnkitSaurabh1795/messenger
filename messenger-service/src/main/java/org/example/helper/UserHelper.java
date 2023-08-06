package org.example.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.example.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserHelper {

    private final UserDetailsService userDetailsService;

    private final IUserDao userDao;

    private final JwtTokenUtil jwtTokenUtil;

    public Boolean isUserLoggedIn(String userName) {
        Optional<UserEntity> userEntity = userDao.fetchByUserName(userName);

        return userEntity.isPresent() && userEntity.get().getToken() != null;
    }

    public Boolean isSessionActive(String userName) {

        Optional<UserEntity> userEntity = userDao.fetchByUserName(userName);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        return jwtTokenUtil.validateToken(userEntity.get().getToken(), userDetails);

    }

    public String getUserID(String userName) {

        return userDao.fetchByUserName(userName).orElseThrow(() ->
                new RuntimeException("User id not found for user " + userName)).getUserId();

    }
}
