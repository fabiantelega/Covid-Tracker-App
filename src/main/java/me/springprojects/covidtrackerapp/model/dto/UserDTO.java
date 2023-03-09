package me.springprojects.covidtrackerapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDTO {

    private String username;
    private String email;
    private String password;
}
