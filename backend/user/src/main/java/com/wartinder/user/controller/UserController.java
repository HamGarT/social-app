package com.wartinder.user.controller;

import com.wartinder.user.entity.User;
import com.wartinder.user.payload.request.CreateUserRequest;
import com.wartinder.user.payload.response.UserDetailResponse;
import com.wartinder.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailResponse> getUser(@PathVariable("userId") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@Valid @RequestBody CreateUserRequest user){
        System.out.println("hola");
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @GetMapping("/userlist")
    public ResponseEntity<List<User>> list(){
        System.out.println("hola");
        return ResponseEntity.ok(userService.listUser());
    }
}
