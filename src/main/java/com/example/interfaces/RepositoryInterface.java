package com.example.interfaces;

import com.example.models.SomeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryInterface extends JpaRepository<SomeData, Integer> {

    List<SomeData> findByPublished(boolean publish);

    List<SomeData> findByTitleContaining(String title);
}
