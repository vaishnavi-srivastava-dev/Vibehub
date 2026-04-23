package com.myorg.vibehub.dto.request;

import com.myorg.vibehub.model.ProfilePicture;
import com.myorg.vibehub.model.User;
import lombok.Data;

@Data
public class PostRequestDto {
    private Long userId;
    private String caption;

    //private User user;
}
