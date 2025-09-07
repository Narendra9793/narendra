package com.assignment.narendra.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    private String description;


    @NotNull(message = "Price cannot be blank.")
    private BigDecimal price;

    @NotBlank(message = "Category cannot be blank.")
    private String category;

    private Boolean inStock;

    private String brand;


    // Media
    @Column(name = "image_url")
    private String imageUrl;

    @JsonBackReference
    @ManyToOne
    private User user;

    
}
