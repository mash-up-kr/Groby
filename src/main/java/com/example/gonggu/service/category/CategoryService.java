package com.example.gonggu.service.category;

import com.example.gonggu.dto.category.CategoryPatchJson;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.persistence.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    //카테고리 생성
    public void createCategory(String category) {
        Category cate = new Category();
        cate.setCategory(category);
        categoryRepo.save(cate);
    }

    public List<Category> getCategory() {
        List<Category> results = new ArrayList<>();
        categoryRepo.findAll().forEach( category ->{
            results.add(category);
                });
        return results;
    }

    //카테고리 변경
    public void updatecategory(CategoryPatchJson acceptJson){
        Category category = categoryRepo.findOne(Long.parseLong(acceptJson.getCategoryId()));
        category.setCategory(acceptJson.getCategory());
        categoryRepo.save(category);
    }

    //카테고리 삭제
    public boolean deletecategory(String category){
        Category checkCategory = categoryRepo.findByCategory(category);
        if(checkCategory == null){
            return false;
        }
        categoryRepo.delete(checkCategory);
        return true;
    }
}

