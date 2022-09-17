package com.example.interfaces;

import com.example.models.AuthLevel;
import com.example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleInterface extends JpaRepository<Role, Integer> {

    Optional<Role> findByAuthLevel(AuthLevel authLevel);
}
