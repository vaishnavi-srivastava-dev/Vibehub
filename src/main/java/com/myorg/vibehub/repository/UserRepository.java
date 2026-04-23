package com.myorg.vibehub.repository;

import com.myorg.vibehub.enums.Gender;
import com.myorg.vibehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findByUsername(String username);
    List<User>findByNameContaining(String name);
    List<User> findByNameContainingAndGender(String name, Gender gender);

    // JPQL -> Java Persistence(Permanent) Query Language
    @Query(value = "SELECT u FROM User u WHERE u.name = :n AND u.gender = :g")
    List<User> searchUserByExactNameAndGender(@Param("n") String name, @Param("g") Gender gender);

    @Query(value = "SELECT u FROM User u WHERE u.email LIKE %:domain%")
    List<User> searchUserByEmail(@Param("domain") String domain);

    //Native Queries
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<User> searchUserByIdUsingNativeQuery(@Param("id") long id);

    @Query(value = "SELECT * FROM users u WHERE u.gender = :gender", nativeQuery = true)
    List<User> searchUserByGenderUsingNativeQuery(@Param("gender") String gender);

}



    /*
    //Dummy Database
    //Long is the key - id which is unique, User is all data from User
    private Map<Long, User> users = new HashMap<>();

    //Make a currentId var since, user would never enter their id upon registration.
    //Id is provided by us to the user and auto-incremented.
    //static, so that it has memory allocated to it already.
    private static Long currentId=0L;

    //method to add a user (Create a user-CRUD)
    public User addUser(User user)
    {
        Long id=++currentId;
        user.setId(id);

        //put() method is used to put data in a map
        users.put(id,user);

        //now we want to see data also-
        //get() method is used to get data from map
        return users.get(id);
    }
    public User getUser(Long id){
       return users.get(id);
    }
    public List<User> getAllUsers(){
        //we took all values of users map and converted it to a linked list
        return new LinkedList<>(users.values());
    }
    public User updateUser(User user){
        if(user.getId()!=null){
            users.put(user.getId(),user);
        }
        return users.get(user.getId());
    }
    public void removeUser(Long id){
        users.remove(id);
    }

     */
