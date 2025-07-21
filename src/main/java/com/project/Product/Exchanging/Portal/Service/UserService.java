package com.project.Product.Exchanging.Portal.Service;

import com.project.Product.Exchanging.Portal.Model.Users;
import com.project.Product.Exchanging.Portal.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create new user with encoded password
    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Get all users
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a single user by ID
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user details
    public Users updateUser(Long id, Users updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            String rawPassword = updatedUser.getPassword();
            if (rawPassword != null && !rawPassword.isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(rawPassword));
            }

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
