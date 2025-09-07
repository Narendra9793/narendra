package com.assignment.narendra.dto;

import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ProductDTO {
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotNull(message = "Price cannot be blank.")
    private BigDecimal price;

    @NotBlank(message = "Category cannot be blank.")
    private String category;

    private String brand;

    private Boolean inStock;

    private MultipartFile imageFile;



}
