package com.myorg.vibehub.startup;


import com.myorg.vibehub.model.NumberOfUser;
import com.myorg.vibehub.repository.NumberOfUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component //to make it's bean
public class UserCounterInitializer {

    @Autowired
    private NumberOfUserRepository numberOfUserRepository;

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args ->{  NumberOfUser numberOfUser = numberOfUserRepository.findById(1L).orElse(null);

            if(numberOfUser == null) {
                numberOfUser = new NumberOfUser();
                numberOfUser.setId(1L);
                numberOfUser.setCounter(0L);
                numberOfUserRepository.save(numberOfUser);
                System.out.println("NumberOfUser object created!");
            }
        };
    }
}
