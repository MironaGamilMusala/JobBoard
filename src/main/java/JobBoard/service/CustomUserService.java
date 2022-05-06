package JobBoard.service;

import JobBoard.Repository.CustomUserRepository;
import JobBoard.model.Authority;
import JobBoard.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {

        CustomUser customUser = customUserRepository.findByUsername(username);
        if (customUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return customUser;
    }

    public CustomUser saveCustomUser(String username, String password){
        CustomUser customUser = new CustomUser(username, passwordEncoder.encode(password));

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_USER", customUser));
        customUser.setAuthorities(authorities);
        return customUserRepository.save(customUser);
    }
}
