package com.example.gonggu.persistence.category;

import com.example.gonggu.domain.category.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
