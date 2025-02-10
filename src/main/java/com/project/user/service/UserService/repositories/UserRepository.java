package com.project.user.service.UserService.repositories;

import com.project.user.service.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
        //Custom queries
}
