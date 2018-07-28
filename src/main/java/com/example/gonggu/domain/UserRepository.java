package com.example.gonggu.domain;
import com.example.gonggu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(String id);


}
