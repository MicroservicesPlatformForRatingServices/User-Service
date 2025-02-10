package com.project.user.service.UserService.services.impl;

import com.project.user.service.UserService.controller.UserController;
import com.project.user.service.UserService.dto.Hotel;
import com.project.user.service.UserService.dto.Rating;
import com.project.user.service.UserService.entities.User;
import com.project.user.service.UserService.exception.ResourceNotFoundException;
import com.project.user.service.UserService.repositories.UserRepository;
import com.project.user.service.UserService.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        logger.info("fetching user details for id :{}",userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User with given id is not available"));
        logger.info("fetched user details from db for user {} , calling rating service to fetch rating details",userId);
        String url = "http://RATINGSERVICE/ratings/getAllRatingsByUser/"+userId;
        //ArrayList ratingsOfUser = restTemplate.getForObject(url,ArrayList.class); - can also be used
        ArrayList<Rating> ratingOfUser = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<ArrayList<Rating>>() {}).getBody();
        logger.info("fetched rating details for the user {}",userId);
        String hotelUrl = "http://HOTELSERVICE/hotels/getHotelById/";
        logger.info("fetched hotel details for user {}",userId);
        for (Rating rating : ratingOfUser){
            Hotel hotel = restTemplate.getForObject(hotelUrl+rating.getHotelId(),Hotel.class);
            rating.setHotel(hotel);
        }
        user.setRatings(ratingOfUser);
        logger.info("fetched all user details for userId {} successully",userId);
        return user;
    }

}
