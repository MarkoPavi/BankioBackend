package com.example.interfaces;

import com.example.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryInterface extends JpaRepository<Contract, Integer> {

    List<Contract> findByPublished(boolean publish);

    List<Contract> findByTitleContaining(String title);
}
