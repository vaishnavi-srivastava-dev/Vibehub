package com.myorg.vibehub.service;

import com.myorg.vibehub.dto.request.CountryRequestDto;
import com.myorg.vibehub.dto.request.LoginRequestDto;
import com.myorg.vibehub.dto.request.ProfilePictureRequestDto;
import com.myorg.vibehub.dto.request.UserRequestDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.LoginResponseDto;
import com.myorg.vibehub.dto.response.UserResponseDto;
import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.model.User;

import java.util.List;

public interface UserService {
    //Response goes to user, that's why we make userResponseDto addUser()

    UserResponseDto addUser(UserRequestDto userRequestDto );
    UserResponseDto getUser(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id,UserRequestDto userRequestDto);
    GenericResponseDto removeUser(Long id);

    //Custom Finder Methods-
    UserResponseDto searchByUsername(String username);
    List<UserResponseDto> searchByName(String name);
    List<UserResponseDto> searchByNameAndGender(String name, Gender gender);

    //JPQL
    List<UserResponseDto> searchUserByExactNameAndGender(String name, Gender gender);
    List<UserResponseDto> searchUserByEmail(String domain);

    //Native Queries
    UserResponseDto searchUserByIdUsingNativeQuery(Long id);
    List<UserResponseDto> searchUserByGenderUsingNativeQuery( Gender gender);

    //Profile Picture
    GenericResponseDto uploadProfilePicture(Long id, ProfilePictureRequestDto profilePictureRequestDto);

    //Country
    GenericResponseDto uploadCountry(Long id, CountryRequestDto countryRequestDto);

    //Login by token
    GenericResponseDto login(LoginRequestDto loginRequestDto);

}
