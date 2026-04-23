package com.myorg.vibehub.service;


import com.myorg.vibehub.dto.request.PostRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {
    GenericResponseDto uploadPost(PostRequestDto postRequestDto);
    PostResponseDto getPost(Long id);
    List<PostResponseDto> getAllPosts();
    PostResponseDto updatePost(Long id,PostRequestDto postRequestDto);
    GenericResponseDto removePost(Long id);
    GenericResponseDto likePost(Long postId);
}
