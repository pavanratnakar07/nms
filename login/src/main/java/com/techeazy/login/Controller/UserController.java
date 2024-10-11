package com.techeazy.login.Controller;

import com.techeazy.login.Entity.LoginRequest;
import com.techeazy.login.Entity.SignupRequest;
import com.techeazy.login.Entity.User;
import com.techeazy.login.Repository.UserRepository;
import com.techeazy.login.Response.ApiResponse;
import com.techeazy.login.Jwt.JwtUtil;
import com.techeazy.login.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;




    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody SignupRequest signupRequest) {
        try {
            User user = new User();
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
         //   user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Hash the password
            user.setPassword(signupRequest.getPassword()); // Store plain text password

            User newUser = userService.signup(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("User registered successfully", newUser));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occurred during signup: " + e.getMessage(), null));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("Login request received for: " + loginRequest.getEmail());
            // Retrieve user from the database
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

//            // Check if the user exists and the password matches
//            if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//                // Generate the JWT token after successful authentication
//                String token = jwtUtil.generateToken(user.getEmail());
//                System.out.println("Login request received for: " + loginRequest.getPassword());
//                return ResponseEntity.ok(new ApiResponse("Login successful", token));
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(new ApiResponse("Invalid email or password", null));
//            }

            // Check if the user exists and the password matches
            if (user != null && loginRequest.getPassword().equals(user.getPassword())) {
                // Generate the JWT token after successful authentication
                String token = jwtUtil.generateToken(user.getEmail());
                System.out.println("Login request received for: " + loginRequest.getPassword());
                return ResponseEntity.ok(new ApiResponse("Login successful", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse("Invalid email or password", null));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occurred during login: " + e.getMessage(), null));
        }
    }
}
