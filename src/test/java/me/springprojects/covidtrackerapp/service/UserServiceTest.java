package me.springprojects.covidtrackerapp.service;

import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.InvalidInputDataException;
import me.springprojects.covidtrackerapp.model.Authority;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.repository.AuthorityRepository;
import me.springprojects.covidtrackerapp.repository.UserRepository;
import me.springprojects.covidtrackerapp.service.verification.UserServiceVerification;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class UserServiceTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final AuthorityRepository authorityRepository = mock(AuthorityRepository.class);
    private final UserServiceVerification userServiceVerification = new UserServiceVerification(userRepository);
    private final UserService userService = new UserService(userRepository, authorityRepository, userServiceVerification);

    @Test
    public void checkIfRegistersAnUser(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        ResponseEntity<UserDTO> res = userService.register(userDTO);

        verify(userRepository, times(1)).save(any());
        verify(authorityRepository, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
    }

    @Test
    public void checkIfThrowsAnErrorWhenNotEnoughData(){
        UserDTO userDTO = UserDTO.builder()
                .username(null)
                .email("correct@email.com")
                .password(null)
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        assertThrows(InvalidInputDataException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfThrowsAnErrorWhenInvalidUsername(){
        UserDTO userDTO = UserDTO.builder()
                .username("inv")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        assertThrows(InvalidInputDataException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfThrowsAnErrorWhenInvalidEmail(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("invalidemail")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        assertThrows(InvalidInputDataException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfThrowsAnErrorWhenInvalidPassword(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("invpass")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        assertThrows(InvalidInputDataException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfThrowsAnErrorWhenUserAlreadyExists(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));
        given(userRepository.findUserByUsername(any())).willReturn(Optional.of(new User()));

        assertThrows(InternalServiceException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfThrowsAnErrorWhenAuthorityNotFound(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.empty());
        given(userRepository.findUserByUsername(any())).willReturn(Optional.of(new User()));

        assertThrows(InternalServiceException.class, () -> userService.register(userDTO));
    }
}