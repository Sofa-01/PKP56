package com.pizzaro.repo;

import com.pizzaro.model.Products;
import com.pizzaro.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
    List<Products> findAllByCategory(Category category);
}
