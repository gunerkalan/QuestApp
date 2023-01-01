package com.guner.questapp.services;

import com.guner.questapp.entities.Comment;
import com.guner.questapp.entities.User;
import com.guner.questapp.repositories.CommentRepository;
import com.guner.questapp.repositories.LikeRepository;
import com.guner.questapp.repositories.PostRepository;
import com.guner.questapp.repositories.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User saveOneUser(User user){
        return userRepository.save(user);
    }

    public User getOneUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User user){
        Optional<User> userDb = userRepository.findById(userId);
        if(userDb.isPresent()){
            User foundUser= userDb.get();
            foundUser.setUserName(user.getUserName());
            foundUser.setPassword(user.getPassword());
            foundUser.setAvatar(user.getAvatar());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteById(Long userId){
        try{
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e) {
            System.out.println("User" + userId + " doesn't exist");
        }
    }

    public User getOneUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId){
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty())
            return null;
        List<Comment> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
    }

}
