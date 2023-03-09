package me.springprojects.covidtrackerapp.controller;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }

    @GetMapping(path = "/all")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }
}
