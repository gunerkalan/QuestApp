package com.guner.questapp.services;

import com.guner.questapp.Response.LikeResponse;
import com.guner.questapp.entities.Like;
import com.guner.questapp.repositories.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    //private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        //this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId){
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()){
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(LikeResponse::new).collect(Collectors.toList());
    }

}
