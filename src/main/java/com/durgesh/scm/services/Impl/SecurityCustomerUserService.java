package com.durgesh.scm.services.Impl;

import com.durgesh.scm.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomerUserService implements UserDetailsService {

   @Autowired
   private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Apne user ko load krna hai
        return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
