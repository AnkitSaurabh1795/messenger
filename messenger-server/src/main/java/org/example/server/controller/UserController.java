package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.request.UserCreateRequest;
import org.example.model.request.UserLoginRequest;
import org.example.service.auth.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@Slf4j
public class UserController {

    private final IUserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        log.info("Request to create user for username {}",request.getUserName());
        return ResponseEntity.ok(userService.createUser(request)) ;
    }

    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) throws Exception {
        log.info("Request to log in user for username {}",request.getUserName());
        return ResponseEntity.ok(userService.loginUser(request));
    }

}
