package com.shad.JobPortal.controller;

import com.shad.JobPortal.entity.User;
import com.shad.JobPortal.repository.UserRepository;
import com.shad.JobPortal.services.UserDetailsServiceImp;
import com.shad.JobPortal.services.UserService;
import com.shad.JobPortal.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody User user) {
        try{
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setName(user.getName());

            userService.saveNewUser(newUser);
            return ResponseEntity.ok("Account created");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Account wasn't created "+e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        try {
            // Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

            // Generate JWT token
            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(user.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Create response map
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while creating authentication token", e);

            // Create error response map
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("status", false);
            response.put("message", "Missing or malformed token");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String token = authHeader.substring(7);
        try {
            boolean isValid = jwtUtil.validateToken(token);
            if (jwtUtil.isTokenExpired(token)) {
                response.put("status", false);
                response.put("message", "Token is expired");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            response.put("status", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", "Invalid token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    @GetMapping("/get-data")
    public ResponseEntity<?> getUserData(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or malformed token");
        }

        String token = authHeader.substring(7); // Extract token by removing "Bearer " prefix
        try {
            Claims claims = jwtUtil.extractAllClaims(token);
            String userId = claims.getSubject();
//            System.out.println(userId);

            User userRepositoryById = (userRepository.findByEmail(userId));
            if(userRepositoryById != null) {

                return ResponseEntity.ok(userRepositoryById);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user exists");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token or user ID: " + e.getMessage());
        }
    }


}
