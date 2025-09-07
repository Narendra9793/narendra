package com.assignment.narendra.utils;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.assignment.narendra.model.Product;


public class ProductSpecifications {

    public static Specification<Product> hasCategory(String category) {
        return (root, query, cb) -> category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Product> priceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min != null && max != null)
                return cb.between(root.get("price"), min, max);
            else if (min != null)
                return cb.greaterThanOrEqualTo(root.get("price"), min);
            else if (max != null)
                return cb.lessThanOrEqualTo(root.get("price"), max);
            return null;
        };
    }
}
