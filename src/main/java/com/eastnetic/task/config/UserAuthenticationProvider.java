package com.eastnetic.task.config;

import com.eastnetic.task.model.entity.Users;
import com.eastnetic.task.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserAuthenticationProvider implements AuthenticationProvider {
    private UsersRepo repository;

    private PasswordEncoder encoder;

    public UserAuthenticationProvider(UsersRepo repository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.repository = repository;
    }

    /**
     * Get the username and password from authentication object and validate with password encoders matching method
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Users> user = Optional.ofNullable(repository.findByUsername(username).orElse(null));
        if (user == null) {
            throw new BadCredentialsException("User not found");
        }

        if (encoder.matches(password, user.get().getPassword())) {
            log.debug("Successfully Authenticated the user");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new UsernamePasswordAuthenticationToken(username, password, getRoles(user.get().getRole()));
        } else {
            throw new BadCredentialsException("Password mismatch");
        }
    }

    /**
     * An user can have more than one roles separated by ",". We are splitting each role separately
     * @param roleList
     * @return
     */
    private List<GrantedAuthority> getRoles(String roleList) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = roleList.split(",");
        for (String role : roles) {
            log.debug("Role: " + role);
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }

        return grantedAuthorityList;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}