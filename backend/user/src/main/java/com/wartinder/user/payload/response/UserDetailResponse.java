package com.wartinder.user.payload.response;

import com.wartinder.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class UserDetailResponse {
    private UUID publicId;
    private String name;
    private String email;
    private String phone;

    public static UserDetailResponse from(User user){
        return UserDetailResponse.builder()
                .publicId(user.getPublicId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
