package com.wartinder.post_service.client;

import com.wartinder.post_service.dto.reponse.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "user", url = "http://localhost:8582/user")
public interface UserClient {
    @GetMapping("/{user-id}")
    Optional<UserResponse> findUserById(@PathVariable Long userId);
}
