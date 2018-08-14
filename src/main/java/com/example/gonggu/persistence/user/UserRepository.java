package com.example.gonggu.persistence.user;

import com.example.gonggu.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByUserId(String id);// Id 값을 통해서 User 를 검색한다.
    public User findByUserName(String userName);

}
