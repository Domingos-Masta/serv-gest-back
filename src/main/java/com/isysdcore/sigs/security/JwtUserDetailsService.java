package com.isysdcore.sigs.security;

import com.isysdcore.sigs.role.Role;
import com.isysdcore.sigs.user.User;
import com.isysdcore.sigs.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Domingos M. Fernando
 */
@Component
public class JwtUserDetailsService implements UserDetailsService
{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        User user = userRepository.findByCred(userName);
        if (null == user) {
            throw new UsernameNotFoundException("Invalid user name or password.");
        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRole()));
    }

//    private List<SimpleGrantedAuthority> getAuthority()
//    {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }
    private List<SimpleGrantedAuthority> getAuthority(Role role)
    {
        return role != null ? Arrays.asList(new SimpleGrantedAuthority(role.getName().trim().toUpperCase())) : Arrays.asList(new SimpleGrantedAuthority("USER"));
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
