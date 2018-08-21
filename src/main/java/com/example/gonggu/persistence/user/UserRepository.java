package com.example.gonggu.persistence.user;

import com.example.gonggu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUserName(String userName);
    public User findByUserEmail(String userEmail);

}
