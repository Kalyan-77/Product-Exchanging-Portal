package com.project.Product.Exchanging.Portal.Service;

import com.project.Product.Exchanging.Portal.Model.Users;
import com.project.Product.Exchanging.Portal.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Users createUser(Users user){
        return userRepository.save(user);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Users updateUser(Long id, Users updatedUser){
        return userRepository.findById(id).map(users -> {
            users.setUsername(updatedUser.getUsername());
            users.setEmail(updatedUser.getEmail());
            users.setPassword(updatedUser.getPassword());
            return userRepository.save(users);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
