package com.example.luyen_tap_1.repository;

import com.example.luyen_tap_1.entity.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<People, String> {
    @Override
    List<People> findAll();

    Page<People> findPeopleByUsernameContains(String userName, Pageable pageable);
}
