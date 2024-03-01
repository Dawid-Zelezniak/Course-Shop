package com.zelezniak.project.repository;

import com.zelezniak.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    Student findByEmail(String email);
}