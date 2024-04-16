package com.zelezniak.project.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByUserCredentialsEmail(String email);

    Student findByUserCredentialsEmail(String email);
}