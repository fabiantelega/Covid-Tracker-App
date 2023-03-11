package me.springprojects.covidtrackerapp.controller;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.service.UserService;
import org.springframework.http.HttpStatus;
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

    @PutMapping(path = "/change-username")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changeUsername(@RequestParam(name = "username") String newUsername){
        userService.changeUsername(newUsername);
    }

    @PutMapping(path = "/change-password")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changeUserPassword(@RequestParam(name = "password") String newPassword){
        userService.changeUserPassword(newPassword);
    }

    @PutMapping(path = "/change-email")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void changeUserEmail(@RequestParam(name = "email") String newEmail){
        userService.changeUserEmail(newEmail);
    }

    @DeleteMapping(path = "/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(){
        userService.deleteUser();
    }
}
