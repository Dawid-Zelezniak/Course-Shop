package com.zelezniak.project.security;

import com.stripe.Stripe;
import com.zelezniak.project.user.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Value("${stripe.api.secretKey}")
    private String secretKey;

    @PostConstruct
    public void initSecretKey() {
        Stripe.apiKey = this.secretKey;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(this.passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthenticationSuccessHandler successHandler) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((configurer) -> {
                    configurer
                            .requestMatchers("/registration/**").permitAll()
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers("/courses").hasRole("STUDENT")
                            .requestMatchers("/add/**").hasRole("TEACHER")
                            .requestMatchers("/save/**").hasRole("TEACHER")
                            .requestMatchers("/author/**").hasRole("TEACHER")
                            .requestMatchers("/all/**").hasRole("ADMIN")
                            .requestMatchers("/checkout/**").hasRole("STUDENT")
                            .requestMatchers("/purchased/courses/**").hasRole("STUDENT")
                            .anyRequest().authenticated();
                })
                .formLogin(form ->
                    form
                            .loginPage("/login/page")
                            .loginProcessingUrl("/login/processing")
                            .defaultSuccessUrl("/courses")
                            .successHandler(successHandler)
                            .usernameParameter("email")
                            .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling((configurer) -> {
                    configurer.accessDeniedPage("/access-denied");
                });
        return httpSecurity.build();
    }
}
