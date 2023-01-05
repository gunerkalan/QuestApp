package com.guner.questapp.controllers;

import com.guner.questapp.Response.UserResponse;
import com.guner.questapp.entities.User;
import com.guner.questapp.exceptions.UserNotFoundException;
import com.guner.questapp.requests.UserDto;
import com.guner.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUser(){
        return userService.getAllUsers().stream().map(UserResponse::new).toList();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto){
        UserDto user = userService.saveOneUser(userDto);
        if(user!=null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId){
        User user = userService.getOneUserById(userId);
        if(user==null){
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody User newUser){
        User user = userService.updateOneUser(userId,newUser);
        if(user!=null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound(){

    }

}
