package clinix.com.clinix_sistema_usuarios.service;

import clinix.com.clinix_sistema_usuarios.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toSet());

                    return new User(
                            user.getUsername(),
                            user.getPassword(),
                            authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Autowired
    private UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .map(user -> {
                            Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                                    .collect(Collectors.toSet());

                            return new User(
                                    user.getUsername(),
                                    user.getPassword(),
                                    authorities);
                        })
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}