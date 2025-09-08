package com.assignment.narendra.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.narendra.repository.ProductRepository;
import com.assignment.narendra.repository.UserRepository;
import com.assignment.narendra.dto.CredentialDTO;
import com.assignment.narendra.model.JwtRequest;
import com.assignment.narendra.model.JwtResponse;
import com.assignment.narendra.model.Product;
import com.assignment.narendra.model.User;
import com.assignment.narendra.security.JwtHelper;
import com.assignment.narendra.service.UserService;
import com.assignment.narendra.utils.ProductSpecifications;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserService userService;

    //localhost:2020/api/auth/allProducts
    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts(
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) List<String> tags) {

            Specification<Product> spec = Specification
            .where(ProductSpecifications.hasCategory(category))
            .and(ProductSpecifications.priceBetween(minPrice, maxPrice));

        List<Product> allProducts = this.productRepository.findAll(spec);
        return ResponseEntity.ok(allProducts);
    }
    
    //localhost:2020/api/auth/allUsers
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = this.userRepository.findAll();
        return ResponseEntity.ok(allUsers);
    }

    //http://localhost:4040/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        this.doAuthenticate(request.getEmail(), request.getPassword());
        

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        System.out.println("Token : " + token);
        //////////////////////////
        User loggedUser=this.userRepository.findByEmail(request.getEmail());
        this.userRepository.save(loggedUser);
        //////////////////////////
        JwtResponse response = JwtResponse.builder()
        .jwtToken(token)
        .email(userDetails.getUsername())
        .userId(loggedUser.getUserId())
        .build();
    
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //http://localhost:2020/api/auth/register
    @PostMapping("/register")
    public String register(@RequestBody CredentialDTO cred){
        String str = this.userService.addUser(cred);
        return "User added with name " + str; 
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            
            System.out.println("Try Started=====>");
            System.out.println(authentication);
            manager.authenticate(authentication);
            System.out.println(authentication);
        } 
        catch (BadCredentialsException e) {
            System.out.println("Catch Started=====>");
            e.printStackTrace();

            throw new BadCredentialsException(" Invalid Username or Password  !!");
            
        }


    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }



}

