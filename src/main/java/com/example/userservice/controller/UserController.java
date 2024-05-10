package com.example.userservice.controller;

import com.example.userservice.entity.PgUser;
import com.example.userservice.model.UserDTO;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAll(){
        return new ResponseEntity<> (userService.getUsers(), HttpStatus.OK);
    }

    @PutMapping("/change-user-status/{id}")
    public void changeStatus(@PathVariable("id") Long id){
        userService.changeStatusUser(id);
    }

    @PutMapping("/change-info")
    public void changeInfo(@RequestBody UserDTO user){
        userService.changeUserInfo(user);
    }

    @PostMapping("/add-user")
    public ResponseEntity<Long> addUser(@RequestBody UserDTO user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @GetMapping("/exist-by-id/{userId}")
    public ResponseEntity<Boolean> existById(@PathVariable("userId") Long userId){
        if (userService.findUser(userId))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/name-by-id/{id}")
    public String getNameById(@PathVariable("id") Long id){
        return userService.getUserName(id);
    }

    @GetMapping("/exist-by-id-for-company/{userId}")
    public boolean existByIdCompany(@PathVariable("userId") Long userId){
        return userService.findUser(userId);
    }
}
