package com.wartinder.social_service.controller;

import com.wartinder.social_service.dto.request.FriendshipRequest;
import com.wartinder.social_service.entity.Friendship;
import com.wartinder.social_service.enums.FriendshipStatus;
import com.wartinder.social_service.repository.FriendshipRepository;
import com.wartinder.social_service.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {
    @Autowired
    private FriendshipService friendshipService;

    @GetMapping
    public ResponseEntity<List<Friendship>> getFriendships() {
        return new ResponseEntity<>(friendshipService.getFriendships(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Friendship> sendFriendRequest(@RequestBody FriendshipRequest friendshipRequest) {
        Friendship createdFriendship = friendshipService.sendFriendRequest(friendshipRequest);
        return new ResponseEntity<>(createdFriendship, HttpStatus.CREATED);
    }

    @GetMapping("/friends/{userId}")
    public ResponseEntity<List<Friendship>> getFriendsList(@PathVariable Long userId) {
        List<Friendship> friends = friendshipService.findAllFriendshipsForUser(userId);
        return ResponseEntity.ok(friends);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Friendship> updateFriendshipStatus(
            @PathVariable Long id,
            @RequestParam FriendshipStatus status) {
        Friendship updatedFriendship = friendshipService.updateFriendshipStatus(id, status);
        return ResponseEntity.ok(updatedFriendship);
    }
}
