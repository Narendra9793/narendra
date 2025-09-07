package com.assignment.narendra.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.narendra.model.User;
import com.assignment.narendra.dto.ProductDTO;
import com.assignment.narendra.model.Product;
import com.assignment.narendra.repository.ProductRepository;
import com.assignment.narendra.repository.UserRepository;
import com.assignment.narendra.service.ProductService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;



    // localhost:2020/api/user/allProducts
    @GetMapping("/allMyProducts")
    public ResponseEntity<List<Product>> getAllMyProducts(Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(user.getMyProducts());
    }

    //  localhost:2020/api/user/addProduct
    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@ModelAttribute  ProductDTO ProductDTO, Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        return ResponseEntity
                .ok("Product with Title " + this.productService.addProduct(ProductDTO, user).getName() + " added!");
    }

    //  localhost:2020/api/user/deleteProduct/{ProductId}
    @DeleteMapping("/deleteProduct/{ProductId}")
    public ResponseEntity<String> addProduct(@PathVariable("ProductId") Long ProductId, Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        Product product=this.productRepository.findByProductId(ProductId);

        if (user.getMyProducts().stream().anyMatch(Product -> Product.equals(product))) {
            user.getMyProducts().removeIf(Product -> Product.equals(product));
            this.productRepository.deleteById(ProductId);
            this.userRepository.save(user);
            return ResponseEntity.ok("Product with Id " + ProductId + " deleted!");
        }
        return ResponseEntity
                .ok("Product with Id " + ProductId + " not found! or You are not authorized to delete this Product!");
    }

    //  localhost:2020/api/user/updateProduct/{ProductId}
    @PostMapping("/updateProduct/{ProductId}")
    public ResponseEntity<String> updateProduct(@PathVariable("ProductId") Long ProductId,@ModelAttribute  ProductDTO productDTO, Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        Product product=this.productRepository.findByProductId(ProductId);
        if (user.getMyProducts().stream().anyMatch(Product -> Product.equals(product))) {
            return ResponseEntity.ok("Product with Id "
                    + this.productService.updateProduct(ProductId, productDTO, user).getProductId() + " updated!");
        }
        return ResponseEntity
                .ok("Product with Id " + ProductId + " not found! or You are not authorized to update this Product!");
    }

    //  localhost:2020/api/user/addTocart/{productId}
    @PostMapping("/addTocart/{productId}")
    public ResponseEntity<String> addTocart(@PathVariable("productId") Long productId, Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        Product product= this.productRepository.findByProductId(productId);

        if (user.getCart().stream().noneMatch(Product -> Product.equals(product))) {
            user.getCart().add(product);
            this.userRepository.save(user);
            return ResponseEntity.ok("Product with Id "+productId+ " is added to cart!");
        }
        return ResponseEntity
                .ok("Product with Id " + productId + " not found! or You have already added it to your cart!");
    }


    //  localhost:2020/api/user/removeFromcart/{ProductId}
    @PostMapping("/removeFromcart/{productId}")
    public ResponseEntity<String> removeFromcart(@PathVariable("productId") Long productId, Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        Product product= this.productRepository.findByProductId(productId);
 
        if (user.getCart().stream().anyMatch(Product -> Product.equals(product)) ) {
            user.getCart().remove(product);
            this.userRepository.save(user);
            return ResponseEntity.ok("Product with Id "+productId+ " is removed from cart!");
        }
        return ResponseEntity
                .ok("Product with Id " + productId + " not found! or You have already removed it from cart!");
    }


    // localhost:2020/api/user/cartProduct
    @GetMapping("/cartProduct")
    public ResponseEntity<List<Product>> cartProduct(Principal principal) {
        User user = this.userRepository.findByEmail(principal.getName());
        return ResponseEntity.ok().body(user.getCart());
    }
}
