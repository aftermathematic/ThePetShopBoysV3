package be.ehb.petshopboys.service;

import be.ehb.petshopboys.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

//
public interface UserService extends UserDetailsService {
    void saveUser(User user);
}
