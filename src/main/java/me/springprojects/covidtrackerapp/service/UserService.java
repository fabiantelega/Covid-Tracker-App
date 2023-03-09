package me.springprojects.covidtrackerapp.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;
import me.springprojects.covidtrackerapp.model.Authority;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.repository.AuthorityRepository;
import me.springprojects.covidtrackerapp.repository.UserRepository;
import me.springprojects.covidtrackerapp.service.verification.UserServiceVerification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserServiceVerification userServiceVerification;

    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<UserDTO> register(UserDTO userDTO){
        userServiceVerification.verificateInputUserData(userDTO); // verificate user's provided data
        Authority authority = authorityRepository.findAuthorityByName("ROLE_USER")
                                                 .orElseThrow(() -> new InternalServiceException(ServiceExceptions.AUTHORITY_NOT_FOUND)); // get basic authority from the database
        User user = User.builder() // build the user
                        .username(userDTO.getUsername())
                        .email(userDTO.getEmail())
                        .password(userDTO.getPassword())
                        .authorities(new HashSet<>())
                        .build();
        user.getAuthorities().add(authority);
        authority.getUsers().add(user);
        userRepository.save(user); // save the user in the database
        authorityRepository.save(authority); // save the modified authority in the database
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll().stream() // get all users from the database
                                       .map(user -> { // map user to their dto
                                           UserDTO userDTO = UserDTO.builder()
                                                   .username(user.getUsername())
                                                   .email(user.getEmail())
                                                   .password(user.getPassword())
                                                   .build();
                                           return userDTO;
                                       })
                                       .collect(Collectors.toList());
    }
}
