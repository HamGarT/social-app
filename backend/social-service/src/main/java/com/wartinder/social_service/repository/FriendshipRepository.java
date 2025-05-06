package com.wartinder.social_service.repository;

import com.wartinder.social_service.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    @Query("SELECT f FROM Friendship f WHERE " +
            "(f.userId = :userId1 AND f.friendId = :userId2) OR " +
            "(f.userId = :userId2 AND f.friendId = :userId1)")
    Optional<Friendship> findFriendshipBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("SELECT f FROM Friendship f WHERE " +
            "((f.userId = :userId OR f.friendId = :userId) AND f.status = 'ACCEPTED')")
    List<Friendship> findAllAcceptedFriendshipsForUser(@Param("userId") Long userId);
}
