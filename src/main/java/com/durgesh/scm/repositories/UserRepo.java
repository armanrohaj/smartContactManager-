package com.durgesh.scm.repositories;

import com.durgesh.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
   //extra methods db related operations
   //custom query methods
   //custom finder method
   Optional<User> findByEmail(String email);

}
