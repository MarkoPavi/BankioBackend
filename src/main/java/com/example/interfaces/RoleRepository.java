package com.example.interfaces;

import com.example.models.AuthLevel;
import com.example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByLevel(AuthLevel authLevel);
}
