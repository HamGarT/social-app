package com.wartinder.social_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipRequest {
    private Long requesterId;
    private Long addresseeId;
}
