package com.wartinder.post_service.service;


import com.wartinder.post_service.entity.Post;
import com.wartinder.post_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post create(Post post){

    }

}
