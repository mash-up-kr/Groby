package com.example.gonggu.persistence.category;

import com.example.gonggu.domain.category.Category;
import com.example.gonggu.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {

}
