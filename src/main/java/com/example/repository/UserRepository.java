package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.User;

/**
 * Repository interface for accessing user data.
 * This interface extends JpaRepository to provide basic CRUD operations for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the User if found, or an empty Optional if no user with the given email exists.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param name The username of the user to find.
     * @return An Optional containing the User if found, or an empty Optional if no user with the given username exists.
     */
    Optional<User> findByName(String name);
}
