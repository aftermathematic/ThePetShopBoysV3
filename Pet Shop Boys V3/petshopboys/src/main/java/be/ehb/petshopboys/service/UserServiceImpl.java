package be.ehb.petshopboys.service;

import be.ehb.petshopboys.model.User;
import be.ehb.petshopboys.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Use constructor injection to avoid circular dependencies
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // This method is used by Spring Security to authenticate users
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Get the user with the given email or throw an exception if it doesn't exist
        User userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
    }

    @Override
    public void saveUser(User userEntity) {
        userRepository.save(userEntity);
    }
}