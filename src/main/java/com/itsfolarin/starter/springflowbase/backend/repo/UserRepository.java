package com.itsfolarin.starter.springflowbase.backend.repo;

import com.itsfolarin.starter.springflowbase.backend.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByUsername(String username);

    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).orElse(null);
    }

}
