package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

/**
 * Controller for managing users.
 * Provides endpoints for user registration, sign-in, sign-out, and deletion.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final String SECRET_KEY = "85dbe15d75ef9308c7ae0f33c7a324cc6f4bf519a2ed2f3027bd33c140a4f9aa";

    @Autowired
    private UserRepository userRepository;

    /**
     * Hashes the password using the SHA-256 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password as a string.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Generates a JWT token for the user.
     *
     * @param name The username.
     * @return The generated token as a string.
     */
    private String generateToken(String name) {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Registers a new user.
     *
     * @param name     The username.
     * @param email    The user's email address.
     * @param password The user's password.
     * @param response HTTP response to set the JWT token in a cookie.
     * @return Response indicating the result of the registration process.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestParam(name = "username") String name, 
                                         @RequestParam(name = "email") String email, 
                                         @RequestParam(name = "password") String password, 
                                         HttpServletResponse response) {
        try {
            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already in use");
            }
            if (userRepository.findByName(name).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Name is already in use");
            }
            String hashedPassword = hashPassword(password);
            User user = new User(name, email, hashedPassword);
            userRepository.save(user);
            String token = generateToken(name);

            // Set the JWT token in the cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 day
            response.addCookie(cookie);
            return ResponseEntity.status(HttpStatus.CREATED).body("Signed up successfully");
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error hashing password");
        }
    }

    /**
     * Signs in a user.
     *
     * @param name     The username.
     * @param password The password.
     * @param response HTTP response to set the JWT token in a cookie.
     * @return Response indicating the result of the sign-in process.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestParam(name = "username") String name, 
                                         @RequestParam(name = "password") String password, 
                                         HttpServletResponse response) {
        try {
            Optional<User> userOptional = userRepository.findByName(name);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            User user = userOptional.get();
            String hashedPassword = hashPassword(password);
            if (!user.getPassword().equals(hashedPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            String token = generateToken(name);

            // Set the JWT token in the cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(false);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 day
            response.addCookie(cookie);

            return ResponseEntity.ok("Signed in successfully");
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }

    /**
     * Signs out the user by removing the JWT token from the cookie.
     *
     * @param response HTTP response to remove the JWT token from the cookie.
     * @return Response indicating the result of the sign-out process.
     */
    @GetMapping("/sign-out")
    public ResponseEntity<String> signOut(HttpServletResponse response) {
        // Remove the JWT token from the cookie
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);

        return ResponseEntity.ok("Signed out successfully");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The user ID.
     * @return The user details if found, or a 404 response if the user is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(userOptional.get());
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The user ID.
     * @return Response indicating the result of the deletion process.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
