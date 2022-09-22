package com.example.interfaces;


import com.example.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findUserDataByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
