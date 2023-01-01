package com.guner.questapp.services;

import com.guner.questapp.Response.LikeResponse;
import com.guner.questapp.Response.PostResponse;
import com.guner.questapp.entities.Post;
import com.guner.questapp.entities.User;
import com.guner.questapp.repositories.LikeRepository;
import com.guner.questapp.repositories.PostRepository;
import com.guner.questapp.repositories.UserRepository;
import com.guner.questapp.requests.PostCreateRequest;
import com.guner.questapp.requests.PostUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private LikeService likeService;
    private UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setLikeService(LikeService likeService){
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId){
        List<Post> list;
        if(userId.isPresent()) {
            list = postRepository.findByUserId(userId.get());
        }else {
            list = postRepository.findAll();
        }

        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.empty(), Optional.of(p.getId()));
            return new PostResponse(p, likes);}).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    public PostResponse getOnePostByIdWithLikes(Long postId){
        Post post = postRepository.findById(postId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.empty(),Optional.of(postId));
        return new PostResponse(post,likes);
    }

    public Post createOnePost(PostCreateRequest newPostRequest){
        Optional<User> user = userRepository.findById(newPostRequest.getUserId());
        if(user.isEmpty())
            return null;
        Post toSave = new Post();
        //toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user.get());
        toSave.setCreateDate(new Date());
        return postRepository.save(toSave);
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest updatePost){
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setText(updatePost.getText());
            toUpdate.setTitle(updatePost.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOnePostById(Long postId){
        postRepository.deleteById(postId);
    }


}
