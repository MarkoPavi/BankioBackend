package com.example.interfaces;

import com.example.models.AuthLevel;
import com.example.models.Contract;
import com.example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Contract, Integer> {

    List<Contract> findByPublished(boolean publish);

    List<Contract> findByTitleContaining(String title);
}
