package com.wartinder.social_service.entity;

import com.wartinder.social_service.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID publicId;
    private Long userId;
    private Long friendId;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    //private LocalDateTime requestedAt;
    //private LocalDateTime respondedAt;

}
