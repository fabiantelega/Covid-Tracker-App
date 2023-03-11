package me.springprojects.covidtrackerapp.service.helper;

import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHelper {

    public User getUserFromSecurityContext(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetailsImpl userDetails){
            return userDetails.getUser();
        }
        throw new InternalServiceException(ServiceExceptions.USER_NOT_FOUND);
    }
}
