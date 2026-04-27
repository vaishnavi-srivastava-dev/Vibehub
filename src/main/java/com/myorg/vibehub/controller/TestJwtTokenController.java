package com.myorg.vibehub.controller;

import com.myorg.vibehub.model.User;
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
    //earlier String Username was taken as a parameter
    public String generateToken(@RequestParam User user){
        //return jwtUtil.generateToken(username);
        return jwtUtil.generateToken(user);
    }

}
