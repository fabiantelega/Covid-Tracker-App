package me.springprojects.covidtrackerapp.repository;

import me.springprojects.covidtrackerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    public Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public Optional<User> findUserByUserEmail(String email);
}
