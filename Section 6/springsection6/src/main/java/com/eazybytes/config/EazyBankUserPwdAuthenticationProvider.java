package com.eazybytes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Profile("!prod")
public class EazyBankUserPwdAuthenticationProvider implements AuthenticationProvider {
    /**
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //1. Loading the user details
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

        //Comparing the pwds
//        if (passwordEncoder.matches(pwd,userDetails.getPassword())){
//            return new UsernamePasswordAuthenticationToken(username,pwd,userDetails.getAuthorities());
//        } else {
//            throw new BadCredentialsException("Invalid Password");
//        }

        return new UsernamePasswordAuthenticationToken(username,pwd,userDetails.getAuthorities());



    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
