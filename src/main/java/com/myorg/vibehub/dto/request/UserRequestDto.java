package com.myorg.vibehub.dto.request;

import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.UserRole;
import lombok.Data;
@Data
public class UserRequestDto {
    //Request doesn't have id
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Gender gender;
    private UserRole role;
    private Long countryId;
}
