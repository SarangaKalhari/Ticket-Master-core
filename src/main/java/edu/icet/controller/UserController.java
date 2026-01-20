package edu.icet.controller;

import edu.icet.model.dto.user.UserRequestDTO;
import edu.icet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public String user(){
        return "Hello Users";
    }

    @PostMapping("/request")
    public void userRequest(@RequestBody UserRequestDTO requestDTO){
        service.getUserRequest(requestDTO);
    }
}
