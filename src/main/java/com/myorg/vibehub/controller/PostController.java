package com.myorg.vibehub.controller;


import com.myorg.vibehub.dto.request.PostRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PostResponseDto;
import com.myorg.vibehub.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<GenericResponseDto> registerPost(@RequestBody PostRequestDto postRequestDto){
        return new ResponseEntity<>(postService.uploadPost(postRequestDto), HttpStatusCode.valueOf(201));
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<GenericResponseDto> likePost(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.likePost(postId), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id),HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.valueOf(200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePosts(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        return new ResponseEntity<>(postService.updatePost(id,postRequestDto),HttpStatusCode.valueOf(200));
    }

    @DeleteMapping
    public ResponseEntity<GenericResponseDto> removePost(@RequestParam Long id){
        return new ResponseEntity<>(postService.removePost(id),HttpStatusCode.valueOf(200));
    }
}
