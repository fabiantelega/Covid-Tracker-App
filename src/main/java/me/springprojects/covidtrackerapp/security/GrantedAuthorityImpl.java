package me.springprojects.covidtrackerapp.security;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.Authority;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {

    private final Authority authority;

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
