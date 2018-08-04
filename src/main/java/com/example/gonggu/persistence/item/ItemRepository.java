package com.example.gonggu.persistence.item;

import com.example.gonggu.domain.item.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    // 등록날짜 내림차순으로 정렬(최신날짜가 먼저)
    public List<Item> findByOrderByRegDateDesc();

    // 인기글 (좋아요순으로 정렬)
    public List<Item> findByOrderByLikeNumDesc();
}
