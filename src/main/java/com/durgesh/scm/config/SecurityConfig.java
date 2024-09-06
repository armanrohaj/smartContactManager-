package com.durgesh.scm.config;

import com.durgesh.scm.services.Impl.SecurityCustomerUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SecurityConfig {
    //user create and login using javacode and in memory service

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN","USER").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder().username("rahulSharma").password("rahul").roles("NULL").build();
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Autowired
    SecurityCustomerUserService userDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //Set userDetailService object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //Set password object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //url configure kiya hai konse public rahenge or konse private rahenge
        httpSecurity.authorizeHttpRequests((authorize) -> {
//            authorize.requestMatchers("/home","/register","services").permitAll();
            authorize.requestMatchers("user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        //custom form login
        //Agar hume kuch bhi change krna hua form login se related to yha aaenge
        httpSecurity.formLogin((formLogin)->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");

//            formLogin.failureForwardUrl("/login?error=true");
//            formLogin.defaultSuccessUrl("/home");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//            formLogin.failureHandler( new AuthenticationFailureHandler() {
//
//                @Override
//                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//
//                }
//            });
//
//            formLogin.successHandler(new AuthenticationSuccessHandler() {
//
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                }
//            });
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(formLogout ->{
                formLogout.logoutUrl("/logout");
                formLogout.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();
    }


}
