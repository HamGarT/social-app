package com.wartinder.social_service.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID publicId;
    private String name;
    private String email;
    private String phone;
}
