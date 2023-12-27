package be.ehb.petshopboys.config;


import be.ehb.petshopboys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Bean for Spring Security Authentication Manager Builder. This is used to authenticate the user.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Authorize requests to the following paths
                .authorizeRequests(authorize -> authorize.requestMatchers("/auth/register", "/auth/login", "/css/**", "/js/**").permitAll().anyRequest().authenticated())
                // Configure login
                .formLogin(formLogin -> formLogin.loginPage("/auth/login").defaultSuccessUrl("/", true))
                // Configure logout
                .logout(logout -> logout.logoutSuccessUrl("/") // URL to redirect to after logout, change if needed
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete cookies
                        .permitAll());

        return http.build();
    }

}

