package com.durgesh.scm.services.Impl;

import com.durgesh.scm.entities.User;
import com.durgesh.scm.helpers.ResourceNotFoundException;
import com.durgesh.scm.repositories.UserRepo;
import com.durgesh.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user1= userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found") );
        //update user1
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setAbout(user.getAbout());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProviderUserId(user.getProviderUserId());

        //Save to the database
        User savedUser = userRepo.save(user1);

        return Optional.of(savedUser);

    }

    @Override
    public void deleteUser(String id) {
        User user1= userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found") );
        userRepo.delete(user1);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user1 = userRepo.findById(userId).orElse(null);
        return user1 != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
     User user1 =  userRepo.findByEmail(email).orElse(null);
     return user1 != null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
