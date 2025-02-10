package com.project.user.service.UserService.services;

import com.project.user.service.UserService.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUser(String userId);

    //TODO: delete
    //TODO: update
}
