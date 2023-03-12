package me.springprojects.covidtrackerapp.service;

import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.InvalidInputDataException;
import me.springprojects.covidtrackerapp.model.Authority;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.repository.AuthorityRepository;
import me.springprojects.covidtrackerapp.repository.UserRepository;
import me.springprojects.covidtrackerapp.service.helper.UserServiceHelper;
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
    private final UserServiceHelper userServiceHelper = mock(UserServiceHelper.class);
    private final UserService userService = new UserService(userRepository, authorityRepository, userServiceVerification, userServiceHelper);

    @Test
    public void checkIfRegistersUser(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));

        UserDTO res = userService.register(userDTO);

        verify(userRepository, times(1)).save(any());
        verify(authorityRepository, times(1)).save(any());
        assertEquals(res, userDTO);
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

    @Test
    public void checkIfThrowsAnErrorWhenEmailAlreadyExists(){
        UserDTO userDTO = UserDTO.builder()
                .username("CorrectUsername")
                .email("correct@email.com")
                .password("CorrectPassword123")
                .build();
        Authority authority = new Authority();
        authority.setUsers(new HashSet<>());
        given(authorityRepository.findAuthorityByName(any())).willReturn(Optional.of(authority));
        given(userRepository.findUserByUserEmail(any())).willReturn(Optional.of(new User()));

        assertThrows(InternalServiceException.class, () -> userService.register(userDTO));
    }

    @Test
    public void checkIfChangesUsername(){
        User user = new User();
        user.setUsername("Username");
        given(userServiceHelper.getUserFromSecurityContext()).willReturn(user);

        userService.changeUsername("NewUsername");

        verify(userRepository, times(1)).save(any());
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    public void checkIfChangesUserPassword(){
        User user = new User();
        user.setPassword("Password");
        given(userServiceHelper.getUserFromSecurityContext()).willReturn(user);

        userService.changeUserPassword("NewPassword");

        verify(userRepository, times(1)).save(any());
        assertEquals("NewPassword", user.getPassword());
    }

    @Test
    public void checkIfChangesUserEmail(){
        User user = new User();
        user.setEmail("email@gmail.com");
        given(userServiceHelper.getUserFromSecurityContext()).willReturn(user);

        userService.changeUserEmail("newemail@gmail.com");

        verify(userRepository, times(1)).save(any());
        assertEquals("newemail@gmail.com", user.getEmail());
    }

    @Test
    public void checkIfDeletesUser(){
        given(userServiceHelper.getUserFromSecurityContext()).willReturn(new User());

        userService.deleteUser();

        verify(userRepository, times(1)).delete(any());
    }
}