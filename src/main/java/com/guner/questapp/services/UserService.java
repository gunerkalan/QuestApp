package com.guner.questapp.services;

import com.guner.questapp.entities.Comment;
import com.guner.questapp.entities.User;
import com.guner.questapp.repositories.CommentRepository;
import com.guner.questapp.repositories.LikeRepository;
import com.guner.questapp.repositories.PostRepository;
import com.guner.questapp.repositories.UserRepository;
import com.guner.questapp.requests.UserDto;
import com.guner.questapp.services.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.userMapper = userMapper;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDto saveOneUser(UserDto userDto){
        User user = userMapper.dtoToEntity(userDto);
        user = userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    public User getOneUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public UserDto updateOneUser(Long userId, UserDto userDto){
        Optional<User> userDb = userRepository.findById(userId);
        if(userDb.isPresent()){
            User foundUser= userDb.get();
            foundUser.setUserName(userDto.getUserName());
            foundUser.setPassword(userDto.getPassword());
            foundUser.setAvatar(userDto.getAvatar());
            foundUser = userRepository.save(foundUser);
            userDto.setId(foundUser.getId());
            return userDto;
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
