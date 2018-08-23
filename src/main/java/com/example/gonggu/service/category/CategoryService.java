package com.example.gonggu.service.category;

import com.example.gonggu.dto.category.CategoryAcceptJson;
import com.example.gonggu.domain.category.Category;
import com.example.gonggu.persistence.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //카테고리 조회
//    public Map<Object, Object> getCategory() {
//        Map<Object, Object> results = new HashMap<>();
//        categoryRepo.findAll().forEach( category -> results.put(category.getCategotyId(), category.getCategory()) );
//        return results;
//    }
    public List<Category> getCategory() {
        List<Category> results = new ArrayList<>();
        categoryRepo.findAll().forEach( category ->{
            results.add(category);
                });
        return results;
    }

    //results.put(category.getCategotyId(), category.getCategory())


    //카테고리 변경
    public void updatecategory(CategoryAcceptJson acceptJson){
        Category category = categoryRepo.findOne(acceptJson.getCategoryId());
        category.setCategory(acceptJson.getCategory());
        categoryRepo.save(category);
    }

//    //카테고리 삭제
//    public boolean deletecategory(Long categoryID){
//        Category checkCategory = categoryRepo.findByCategoryId(categoryID);
//
//
//        if(checkCategory == null){
//            return false;
//        }
//
//        categoryRepo.delete(checkCategory);
//        return true;
//    }
}

