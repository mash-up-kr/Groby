package com.example.gonggu.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    // 등록날짜 내림차순으로 정렬(최신날짜가 먼저)
    public List<Item> findByOrderByRegDateDesc();


}
