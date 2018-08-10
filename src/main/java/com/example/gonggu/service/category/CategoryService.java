package com.example.gonggu.service.category;

import com.example.gonggu.persistence.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepo;
}
