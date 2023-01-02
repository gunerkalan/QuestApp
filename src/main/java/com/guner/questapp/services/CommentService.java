package com.guner.questapp.services;

import com.guner.questapp.Response.CommentResponse;
import com.guner.questapp.entities.Comment;
import com.guner.questapp.entities.Post;
import com.guner.questapp.entities.User;
import com.guner.questapp.repositories.CommentRepository;
import com.guner.questapp.repositories.PostRepository;
import com.guner.questapp.repositories.UserRepository;
import com.guner.questapp.requests.CommentCreateRequest;
import com.guner.questapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId){

        List<Comment> comments;
        if(userId.isPresent() && postId.isPresent()){
            comments= commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()){
            comments = commentRepository.findByUserId(userId.get());
        }else if(postId.isPresent()){
            comments = commentRepository.findByPostId(postId.get());
        }else{
            comments = commentRepository.findAll();
        }
        return comments.stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    public Comment getOneCommentById(Long commentId){
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request){
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Post> post = postRepository.findById(request.getPostId());
        if(user.isPresent() && post.isPresent()){
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(post.get());
            commentToSave.setUser(user.get());
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }
        else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(request.getText());
            return commentRepository.save(commentToUpdate);
        }else
            return null;
    }

    public void deleteOneCommentById(Long commentId){
        commentRepository.deleteById(commentId);
    }

}
