package com.example.gonggu.category;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.persistence.category.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryTests {
    @Autowired
    CategoryRepository categoryRepo;

    @Test
    public void insertCategory(){
        Category cate = new Category();
//        cate.setCategory("의류");
//        cate.setCategory("잡화");
//        cate.setCategory("식품");
        cate.setCategory("화장품");
//        cate.setCategory("기타");
        categoryRepo.save(cate);
    }
}
