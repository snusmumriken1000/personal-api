package com.example.graphql;

import com.example.entity.User;
import com.example.repository.UserRepository;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class UserResource {
    
    @Inject
    UserRepository userRepository;
    
    @Query("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Query
    public User getUser(@Name("id") Long id) {
        return userRepository.findById(id);
    }
    
    @Mutation
    public User createUser(User user) {
        return userRepository.create(user);
    }
    
    @Mutation
    public User updateUser(User user) {
        return userRepository.update(user);
    }
    
    @Mutation
    public boolean deleteUser(@Name("id") Long id) {
        userRepository.delete(id);
        return true;
    }
} 