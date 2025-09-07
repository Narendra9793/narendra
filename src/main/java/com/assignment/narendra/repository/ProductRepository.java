package com.assignment.narendra.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.assignment.narendra.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    
    public Product findByProductId(Long productId);

    public List<Product>findByCategory(String category);

    public List<Product>findByPrice(BigDecimal Price);

    @Query("SELECT p FROM Product p WHERE p.price = :price AND p.category = :category")
    List<Product> findByPriceAndCategory(@Param("category") String category,@Param("price") BigDecimal price);

}
