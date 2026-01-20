package edu.icet.service;

import edu.icet.model.dto.user.UserRequestDTO;
import edu.icet.model.entity.User;
import edu.icet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private long generateID(){
        return repository.getLastID()+1;
    }

    public void getUserRequest(UserRequestDTO requestDTO) {

        User user = new User(
                generateID(),
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getTier()
        );

        repository.createUser(user);

    }
}
