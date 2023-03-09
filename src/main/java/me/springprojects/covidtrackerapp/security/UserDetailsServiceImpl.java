package me.springprojects.covidtrackerapp.security;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.User;
import me.springprojects.covidtrackerapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user.map(u -> new UserDetailsImpl(u, passwordEncoder))
                   .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
