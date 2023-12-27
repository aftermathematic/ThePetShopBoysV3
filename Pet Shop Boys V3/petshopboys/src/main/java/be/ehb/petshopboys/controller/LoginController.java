package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.model.User;
import be.ehb.petshopboys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // Constructor injection
    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint for the login form
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Endpoint for the registration form
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; //
    }

    // Endpoint for registering a user
    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
        return "redirect:/auth/login";
    }
}