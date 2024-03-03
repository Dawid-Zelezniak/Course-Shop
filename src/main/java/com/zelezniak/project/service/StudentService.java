package com.zelezniak.project.service;

import com.zelezniak.project.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student findByEmail(String email);
}