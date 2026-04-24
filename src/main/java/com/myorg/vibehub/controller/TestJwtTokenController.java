package com.myorg.vibehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.myorg.vibehub.utility.JwtUtil;

@RestController
@RequestMapping("/api/test/jwt")
public class TestJwtTokenController {

    //dependency injection of JwtUtil Bean
    @Autowired
    private JwtUtil jwtUtil;

    //generate token
    @GetMapping
    public String generateToken(@RequestParam String username){
        return jwtUtil.generateToken(username);
    }

}
