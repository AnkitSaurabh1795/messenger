package org.example.service.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IUserDao;
import org.example.entity.UserEntity;
import org.example.model.request.UserCreateRequest;
import org.example.model.request.UserLoginRequest;
import org.example.model.response.AllUserResponse;
import org.example.service.auth.IUserService;
import org.example.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.util.Util.generateUserId;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUserService jwtUserService;

    private final JwtTokenUtil jwtTokenUtil;
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
                    .password(bCryptPasswordEncoder.encode(user.getPassCode())).role("DEFAULT").build());
            log.info("User {} created Successfully ", user.getUserName());
            return "Success";
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
        if (info != null && info.getToken() == null) {
            Authentication authentication = null;
            try {
                authentication = authenticate(user.getUserName(),
                        user.getPassCode());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = jwtUserService.loadUserByUsername(user.getUserName());
            final String token = jwtTokenUtil.generateToken(userDetails);
            info.setToken(token);
            userDao.updateById(info.getUserId(), info);
            log.info("User {} successfully logged in", user.getUserName());
            return "Success  " + token;
        }
        log.info("User {} is already logged in", user.getUserName());
        return "User is already logged in";

    }

    @Override
    public String logoutUser(String userName) {
        Optional<UserEntity> userEntity = userDao.fetchByUserName(userName);
        UserEntity info = userEntity.orElse(null);
        log.info("UserInfo:{}",info);
        if(info == null) {
            return "User does not exist";
        }
        else if (info != null && info.getToken() != null) {
            info.setToken(null);
            userDao.updateById(info.getUserId(), info);
            log.info("User {} successfully logged out", userName);
            return "User is successfully logged out";
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
        AllUserResponse response = AllUserResponse.builder().users(new ArrayList<>()).build();
        userEntities.forEach(user -> {
            response.getUsers().add(user.getUserName());
        });
        return response;
    }

    private Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        return authentication;
    }

}
