package com.myorg.vibehub.controller;

import com.myorg.vibehub.dto.response.ServerResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//We made this Server Controller just to ensure that our service is running live!
//Make this class first in any project.
@RestController
@RequestMapping("/")
public class ServerController
{
    @GetMapping("/test")
    public String testApi()
    {
        return "TEST API - GET";
    }

    @PostMapping
    public String testApiPost() {
        return "TEST API - POST";
    }

    @GetMapping
    public ResponseEntity<ServerResponseDto> serverStatus() {
        //ResponseEntity → a wrapper for an HTTP response
        return new ResponseEntity<>(new ServerResponseDto(), HttpStatusCode.valueOf(200));
    }
}
