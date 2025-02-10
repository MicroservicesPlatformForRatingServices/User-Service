package com.project.user.service.UserService.controller;

import com.project.user.service.UserService.entities.User;
import com.project.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        //return new ResponseEntity<>(userService.saveUser(user),HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
    }

    @GetMapping("/getUserById/{id}")
    @CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "fallbackMethodForRatingHotel")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    public ResponseEntity<User> fallbackMethodForRatingHotel(String id, Exception e){
        logger.info("directed to fallback method for userId {}",id);
        User user = User.builder().about("can't fetch current rating due to service down, please try again later")
                .build();
        return new ResponseEntity<>(user,HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
}
