package com.example.gonggu.persistence.user;

import com.example.gonggu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    public List<User> findByUserId(String userId);
}
