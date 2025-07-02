package com.example.sudokuapp.repository;

import com.example.sudokuapp.entity.SolveRecord;
import com.example.sudokuapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolveRecordRepository extends JpaRepository<SolveRecord, Long> {
    List<SolveRecord> findByUser(User user);
}