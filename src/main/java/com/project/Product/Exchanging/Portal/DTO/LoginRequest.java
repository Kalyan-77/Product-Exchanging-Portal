package com.project.Product.Exchanging.Portal.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
    //private String username;
}
