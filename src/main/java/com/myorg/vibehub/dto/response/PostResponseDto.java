package com.myorg.vibehub.dto.response;


import com.myorg.vibehub.model.User;
import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;

    private String caption;
    private Long likeCount;
    private Long commentCount;
    private Long shareCount;
    private User user;
}
