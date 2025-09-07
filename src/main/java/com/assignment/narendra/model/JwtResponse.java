package com.assignment.narendra.model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private String jwtToken;
    private String email;
    private Long userId;
}
