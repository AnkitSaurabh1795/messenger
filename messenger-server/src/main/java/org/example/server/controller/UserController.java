package org.example.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.request.UserCreateRequest;
import org.example.model.request.UserLoginRequest;
import org.example.model.response.AllUserResponse;
import org.example.service.auth.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@Slf4j
public class UserController {

    private final IUserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<String> createUser(@RequestBody UserCreateRequest request) {
        log.info("Request to create user for username {}",request.getUserName());
        return ResponseEntity.ok(userService.createUser(request)) ;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request) throws Exception {
        log.info("Request to log in username {}",request.getUserName());
        return ResponseEntity.ok(userService.loginUser(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String userName) {
        log.info("Request to log out username {}",userName);
        return ResponseEntity.ok(userService.logoutUser(userName));
    }

    @GetMapping("/get/users")
    public ResponseEntity<AllUserResponse> fetchAllUser() {
        log.info("Request to get all users");
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

}
