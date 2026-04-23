package com.myorg.vibehub.dto.response;

import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.enums.UserRole;
import com.myorg.vibehub.model.Country;
import com.myorg.vibehub.model.ProfilePicture;
import com.myorg.vibehub.model.Wallet;
import lombok.Data;

@Data
public class UserResponseDto {

    //response doesn't have password
    private Long id;
    private String name;
    private String userName;
    private String email;
    private String phone;
    private Gender gender;
    private UserRole role;
    //add wallet to show wallet's data
    private Wallet wallet;
    private ProfilePicture profilePicture;
    private CountryResponseDto country;
}
