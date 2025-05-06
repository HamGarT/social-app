package com.wartinder.social_service.service;

import com.wartinder.social_service.client.UserClient;
import com.wartinder.social_service.dto.request.FriendshipRequest;
import com.wartinder.social_service.entity.Friendship;
import com.wartinder.social_service.repository.FriendshipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wartinder.social_service.enums.FriendshipStatus;

import java.util.List;

@Service
public class FriendshipService {

    private FriendshipRepository friendshipRepository;
    private UserClient userClient;

    public FriendshipService(UserClient userClient, FriendshipRepository friendshipRepository) {
        this.userClient = userClient;
        this.friendshipRepository = friendshipRepository;
    }

    public List<Friendship> getFriendships() {
        return friendshipRepository.findAll();
    }

    @Transactional
    public Friendship sendFriendRequest(FriendshipRequest friendshipRequest) {
        friendshipRepository.findFriendshipBetweenUsers(
                        friendshipRequest.getRequesterId(),
                        friendshipRequest.getAddresseeId())
                .ifPresent(f -> {
                    throw new IllegalStateException("Friendship already exists between these users");
                });
        if(userClient.exists(friendshipRequest.getRequesterId()) && userClient.exists(friendshipRequest.getAddresseeId())) {
            Friendship friendship = new Friendship();
            friendship.setUserId(friendshipRequest.getRequesterId());
            friendship.setFriendId(friendshipRequest.getAddresseeId());
            friendship.setStatus(FriendshipStatus.PENDING);
            userClient.get(friendshipRequest.getRequesterId());
            return friendshipRepository.save(friendship);
        }
        return null;
    }

    @Transactional
    public Friendship updateFriendshipStatus(Long friendshipId, FriendshipStatus status) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new EntityNotFoundException("Friendship not found with id: " + friendshipId));

        friendship.setStatus(status);
        return friendshipRepository.save(friendship);
    }

    public List<Friendship> findAllFriendshipsForUser(Long userId) {
        return friendshipRepository.findAllAcceptedFriendshipsForUser(userId);
    }
}
