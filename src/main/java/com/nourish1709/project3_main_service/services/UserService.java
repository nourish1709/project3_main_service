package com.nourish1709.project3_main_service.services;

import com.nourish1709.project3_main_service.daos.UserRepository;
import com.nourish1709.project3_main_service.security.entity.User;
import com.nourish1709.project3_main_service.exceptions.IncorrectCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static com.nourish1709.project3_main_service.security.entity.enums.UserRole.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String SECRET = "secret";
    private static final String EMAIL = "email";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(IncorrectCredentialsException::new);
    }

    @PostConstruct
    private void addUsers() {
        List<User> users = Arrays.asList(
                new User(
                        1L,
                        "Oleg",
                        passwordEncoder.encode(SECRET),
                        EMAIL,
                        ADMIN
                ),
                new User(
                        2L,
                        "Roman",
                        passwordEncoder.encode(SECRET),
                        EMAIL,
                        ADMIN
                ),
                new User(
                        3L,
                        "Andriana",
                        passwordEncoder.encode(SECRET),
                        EMAIL,
                        ADMIN
                ),
                new User(
                        4L,
                        "Zhenya",
                        passwordEncoder.encode(SECRET),
                        EMAIL,
                        ADMIN
                ),
                new User(
                        5L,
                        "user",
                        passwordEncoder.encode(SECRET),
                        EMAIL,
                        USER
                )
        );

        userRepository.saveAll(users);
    }
}
