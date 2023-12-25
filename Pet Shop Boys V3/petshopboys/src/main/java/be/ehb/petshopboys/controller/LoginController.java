package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.model.User;
import be.ehb.petshopboys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Login form
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Registration form
    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User()); // Adds an empty User object for the form binding
        return "register"; // Returns the register.html template
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);
        return "redirect:/auth/login";
    }
}