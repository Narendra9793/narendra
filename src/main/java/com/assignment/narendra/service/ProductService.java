package com.assignment.narendra.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.narendra.dto.ProductDTO;
import com.assignment.narendra.model.Product;
import com.assignment.narendra.model.User;
import com.assignment.narendra.repository.ProductRepository;
import com.assignment.narendra.repository.UserRepository;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    public Product addProduct(ProductDTO productDTO, User user) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setBrand(productDTO.getBrand());
        product.setCategory(productDTO.getCategory());
        product.setUser(user);

        try {
            product.setImageUrl(this.fileService.uploadMedia(productDTO.getImageFile(), user.getUserId()) );
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.productRepository.save(product);
        user.getMyProducts().add(product);

        this.userRepository.save(user);
        return product;
    }

    public Product updateProduct(Long productId, ProductDTO productDTO, User user) {
        Product product = productRepository.findByProductId(productId);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setCategory(productDTO.getCategory());
        product.setInStock(productDTO.getInStock());
        
        try {
            product.setImageUrl(this.fileService.uploadMedia(productDTO.getImageFile(), user.getUserId()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return this.productRepository.save(product);
    }
}
