package com.wartinder.user.service;

import com.wartinder.user.entity.User;
import com.wartinder.user.payload.response.UserDetailResponse;
import com.wartinder.user.repository.UserRepository;

import com.wartinder.user.payload.request.CreateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(CreateUserRequest createUserRequest) {
        return userRepository.save(CreateUserRequest.to(createUserRequest));
    }
    public UserDetailResponse getUserById(Long id) {
        return UserDetailResponse.from(userRepository.findById(id).get()) ;
    }
    public List<User> listUser() {
       return userRepository.findAll();
    }

}
