package org.example.service.auth;

import org.example.model.request.UserCreateRequest;
import org.example.model.request.UserLoginRequest;

public interface IUserService {
    String createUser(UserCreateRequest userCreateRequest);

    String loginUser(UserLoginRequest userLoginRequest) throws Exception;

    String logoutUser(String userName);
}
