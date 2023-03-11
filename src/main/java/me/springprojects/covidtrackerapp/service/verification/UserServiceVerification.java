package me.springprojects.covidtrackerapp.service.verification;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.InvalidInputDataException;
import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;
import me.springprojects.covidtrackerapp.exceptions.enums.UserExceptions;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.model.dto.UserDTO;
import me.springprojects.covidtrackerapp.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class UserServiceVerification {

    private static final Pattern VALID_USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{6,15}$");
    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile("^[0-9A-Za-z$@#.]{8,20}$");
    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile("^[A-Za-z]{4,10}@[A-Za-z.]{2,5}\\.[a-z]{2,4}$");

    private final UserRepository userRepository;

    public void verificateInputUserData(UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String email = userDTO.getEmail();
        if(username == null || email == null || password == null)
            throw new InvalidInputDataException(UserExceptions.NOT_ENOUGH_DATA);

        verificateIfUserExists(username);
        verificateIfEmailExists(email);
        verificateUsername(username);
        verificateUserEmail(email);
        verificateUserPassword(password);
    }

    public void verificateUsername(String username){
        Matcher match = VALID_USERNAME_PATTERN.matcher(username);
        if(!match.find()) throw new InvalidInputDataException(UserExceptions.INVALID_USERNAME);
    }

    public void verificateUserEmail(String email){
        Matcher match = VALID_EMAIL_PATTERN.matcher(email);
        if(!match.find()) throw new InvalidInputDataException(UserExceptions.INVALID_EMAIL);
    }

    public void verificateUserPassword(String password){
        Matcher match = VALID_PASSWORD_PATTERN.matcher(password);
        if(!match.find()) throw new InvalidInputDataException(UserExceptions.INVALID_PASSWORD);
    }

    public void verificateIfUserExists(String username){
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) throw new InternalServiceException(ServiceExceptions.USER_EXISTS);
    }

    public void verificateIfEmailExists(String email){
        Optional<User> user = userRepository.findUserByUserEmail(email);
        if(user.isPresent()) throw new InternalServiceException(ServiceExceptions.EMAIL_EXISTS);
    }
}
