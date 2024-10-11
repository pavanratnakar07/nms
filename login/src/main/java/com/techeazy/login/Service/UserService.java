package com.techeazy.login.Service;
import com.techeazy.login.Entity.User;
import com.techeazy.login.Exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.techeazy.login.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signup(User user) {
        // Logic to save the user (e.g., password hashing) should be implemented here
       // user.setPassword(passwordEncoder.encode(user.getPassword()));
       // System.out.println("Encoded password: " + user.getPassword()); // Debugging line
        System.out.println("Plain password: " + user.getPassword()); // Debugging line
        return userRepository.save(user);
    }




    // for checking whether email already exists or not
    public User signupq(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        // Hash the password before saving
      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(String email, String rawPassword) {
        // Logic to validate the user credentials (use password encoder for checking password)
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
          //  System.out.println(passwordEncoder.matches(user.getPassword() ,password));

//            String encodedRawPassword = passwordEncoder.encode(rawPassword);
//            System.out.println("Raw password: " + rawPassword); // The original raw password
//            System.out.println("Encoded raw password: " + encodedRawPassword); // The hashed version of the raw password
//
//            System.out.println("Stored password: " + user.getPassword());

            System.out.println("Stored password: " + user.getPassword()); // Debugging line
            System.out.println("Raw password: " + rawPassword); // The original raw password

            // Check if the plain text password matches
            if (rawPassword.equals(user.getPassword())) {
                return user; // Successful login
            }
        }



//            // Check if the password matches the hashed password
//            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
//                return user; // Successful login
//            }

        return null; // Return null if login fails
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles("USER") // Assign roles as needed
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}
