package edu.fudanselab.trainticket.init;

import edu.fudanselab.trainticket.entity.AuthUser;
import edu.fudanselab.trainticket.entity.User;
import edu.fudanselab.trainticket.repository.AuthUserRepository;
import edu.fudanselab.trainticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author fdse
 */
@Component
public class AuthInitUser implements CommandLineRunner {

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Override
    public void run(String... strings) throws Exception {
        AuthUser whetherExistUser = userRepository.findByUsername("fdse_microservice").orElse(new AuthUser());
        if (whetherExistUser.getUsername() == null) {
            AuthUser user = AuthUser.builder()
                    .userId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
                    .username("fdse_microservice")
                    .password(passwordEncoder.encode("111111"))
                    .roles(new HashSet<>(Arrays.asList("ROLE_USER")))
                    .build();
            userRepository.save(user);
        }

        AuthUser whetherExistAdmin = userRepository.findByUsername("admin").orElse(new AuthUser());
        if (whetherExistAdmin.getUsername() == null) {
            AuthUser admin = AuthUser.builder()
                    .userId(UUID.randomUUID().toString())
                    .username("admin")
                    .password(passwordEncoder.encode("222222"))
                    .roles(new HashSet<>(Arrays.asList("ROLE_ADMIN")))
                    .build();
            userRepository.save(admin);
        }
    }
}
