package com.myorg.vibehub.service;

import com.myorg.vibehub.dto.request.CountryRequestDto;
import com.myorg.vibehub.dto.request.ProfilePictureRequestDto;
import com.myorg.vibehub.dto.request.UserRequestDto;
import com.myorg.vibehub.dto.response.CountryResponseDto;
import com.myorg.vibehub.dto.response.GenericResponseDto;
import com.myorg.vibehub.dto.response.UserResponseDto;
import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.exception.UserNotFoundException;
import com.myorg.vibehub.model.*;
import com.myorg.vibehub.repository.CountryRepositiry;
import com.myorg.vibehub.repository.NumberOfUserRepository;
import com.myorg.vibehub.repository.ProfilePictureRepository;
import com.myorg.vibehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private CountryRepositiry countryRepositiry;

    @Autowired
    private NumberOfUserRepository numberOfUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto addUser(UserRequestDto userRequestDto)
    {
        NumberOfUser numberOfUser = numberOfUserRepository.findById(1L).orElse(null);
        numberOfUser.setCounter(numberOfUser.getCounter() + 1);
        numberOfUserRepository.save(numberOfUser);

        //Adding add wallet code here
        //wallet must be created immediately when user is created
        User user = new User();

        Wallet wallet = new Wallet();
        wallet.setBalance(0D);
        wallet.setUser(user);

        user.setWallet(wallet);

        userRepository.save(user);

        //Adding add Country code here.
        //Country must be set immediately when user is created.

        Country country = countryRepositiry.findById(userRequestDto.getCountryId()).orElse(null);

        user.setCountry(country);

        userRepository.save(user);
        //converting UserRequestDto to User, so that we can store it in database
        mapUserRequestDtoToUser(userRequestDto,user);

        User storedUser = userRepository.save(user);

        return mapUserToUserResponseDto(storedUser);
    }

    @Override
    public UserResponseDto getUser(Long id) {
//        User user = userRepository.findById(id).orElse(null);
//        //we have to convert User to UserResponse Dto
//        return mapUserToUserResponseDto(user);

        //findBy gives exception so put orElse(null)

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User id: " + id + " doesn't exist"));
        return mapUserToUserResponseDto(user);

        //return mapUserToUserResponseDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> userList=userRepository.findAll();
        List<UserResponseDto> userResponseDtoList=new LinkedList<>();
        //we have to convert list of User to list of UserResponseDto
        // means we can use add() method of LinkedList and add each userList member
        // to userResponseDtoList by using Foreach Loop

        for(User user: userList){
            UserResponseDto userResponseDto=mapUserToUserResponseDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user=userRepository.findById(id).orElse(null);
        //userRequestDto->new data , user has old data. map method overrides old data to new data
        mapUserRequestDtoToUser(userRequestDto,user);
        userRepository.save(user);
        //updateUser() method returns User, now we have to convert it to UserResponseDto
        //so that I can return it.
        return mapUserToUserResponseDto(user);

    }

    @Override
    public GenericResponseDto removeUser(Long id) {
        User user=userRepository.findById(id).orElse(null);
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        if(user!=null){
            String name = user.getName();
            userRepository.deleteById(id);
            genericResponseDto.setIsSuccess(true);
            genericResponseDto.setMessage("User Name- " + name + " removed successfully");
        }
        else{
            genericResponseDto.setIsSuccess(false);
            genericResponseDto.setMessage("User id- " + id + " doesn't exist");
        }
        return genericResponseDto;
    }

    @Override
    public UserResponseDto searchByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return mapUserToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> searchByName(String name) {
        List<User> userList = userRepository.findByNameContaining(name);
        List<UserResponseDto> userResponseDtoList = new LinkedList<>();

        for(User user : userList){
            userResponseDtoList.add(mapUserToUserResponseDto(user));
        }
        return userResponseDtoList;
    }

    @Override
    public List<UserResponseDto> searchByNameAndGender(String name, Gender gender) {
        List<User> userList = userRepository.findByNameContainingAndGender(name,gender);
        List<UserResponseDto> userResponseDtoList = new LinkedList<>();

        for(User user : userList){
            userResponseDtoList.add(mapUserToUserResponseDto(user));
        }
        return userResponseDtoList;
    }

    //JPQL
    @Override
    public List<UserResponseDto> searchUserByExactNameAndGender(String name, Gender gender) {
        List<User> userList = userRepository.searchUserByExactNameAndGender(name,gender);
        List<UserResponseDto> userResponseDtoList = new LinkedList<>();

        for(User user : userList){
            userResponseDtoList.add(mapUserToUserResponseDto(user));
        }
        return userResponseDtoList;
    }

    @Override
    public List<UserResponseDto> searchUserByEmail(String domain) {
        //for searching @gmail and not just gmail anywhere
        List<User> userList = userRepository.searchUserByEmail("@"+domain);
        return mapUserListToUserResponseDtoList(userList);
    }

    //Native Query
    @Override
    public UserResponseDto searchUserByIdUsingNativeQuery(Long id) {
        User user = userRepository.searchUserByIdUsingNativeQuery(id).orElse(null);
        return mapUserToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> searchUserByGenderUsingNativeQuery(Gender gender) {
        List<User> userList = userRepository.searchUserByGenderUsingNativeQuery(gender.name());
        return mapUserListToUserResponseDtoList(userList);
    }

    @Override
    public GenericResponseDto uploadProfilePicture(Long id, ProfilePictureRequestDto profilePictureRequestDto)
    {
        User user = userRepository.findById(id).orElse(null);
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setUrl(profilePictureRequestDto.getUrl());
        profilePicture.setAlternativeText(user.getName()+"'s profile picture not found!");
        profilePicture.setUser(user);

        //Save id in database
        profilePicture = profilePictureRepository.save(profilePicture);

        //Now set the profile picture in User
        user.setProfilePicture(profilePicture);

        //Update user-update Profile Pic id in database
        userRepository.save(user);

//        //Save id in database
//        profilePicture = profilePictureRepository.save(profilePicture);

        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setIsSuccess(true);
        genericResponseDto.setDetails(Map.of("profile_id",user.getProfilePicture().getId()));
        genericResponseDto.setMessage(user.getName()+"'s profile picture as been uploaded successfully!");
        return genericResponseDto;
    }

    @Override
    public GenericResponseDto uploadCountry(Long id, CountryRequestDto countryRequestDto) {

        User user = userRepository.findById(id).orElse(null);
        Country country = new Country();
        country.setName(countryRequestDto.getName());
        country.setSlug(countryRequestDto.getSlug());
        country.setCode(countryRequestDto.getCode());

        country = countryRepositiry.save(country);
        user.setCountry(country);
        userRepository.save(user);

        GenericResponseDto genericResponseDto = new GenericResponseDto();
        genericResponseDto.setIsSuccess(true);
        genericResponseDto.setDetails(Map.of("country id",user.getCountry().getId()));
        genericResponseDto.setMessage(user.getName()+"'s Country has been uploaded successfully!");
        return genericResponseDto;
    }


    //Helper Methods
    //Map User to UserResponseDto
    private UserResponseDto mapUserToUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setUserName(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setGender(user.getGender());

        userResponseDto.setWallet(user.getWallet());
        userResponseDto.setProfilePicture(user.getProfilePicture());

        CountryServiceImpl countryService = new CountryServiceImpl();

        CountryResponseDto countryResponseDto = countryService.mapCountryToCountryResponseDto(user.getCountry());
        userResponseDto.setCountry(countryResponseDto);

        return userResponseDto;
    }

    //Map UserRequestDto to User, so that we can store it in database-
    private User mapUserRequestDtoToUser(UserRequestDto userRequestDto, User user){
        user.setName(userRequestDto.getName());
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setGender(userRequestDto.getGender());
        user.setRole(userRequestDto.getRole());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        return user;
    }

    //Map userList to userResponseDtoList
    private List<UserResponseDto> mapUserListToUserResponseDtoList(List<User> userList){
        List<UserResponseDto> userResponseDtoList = new LinkedList<>();

        for(User user : userList){
            userResponseDtoList.add(mapUserToUserResponseDto(user));
        }
        return userResponseDtoList;
    }
}


