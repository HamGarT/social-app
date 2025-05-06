package com.wartinder.social_service.client;

import com.wartinder.social_service.dto.response.UserResponse;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user", url = "http://localhost:8582/user")
public interface UserClient {

    @GetMapping("/{id}")
    UserResponse get(@PathVariable("id") Long userId);

    @GetMapping("/{id}")
    default boolean exists(@PathVariable("id") Long userId) {
        try{
            get(userId);
            return true;
        } catch (FeignException.NotFound e) {
            return false;
        }
    }
}
