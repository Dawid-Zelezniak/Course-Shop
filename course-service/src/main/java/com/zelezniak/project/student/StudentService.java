package com.zelezniak.project.student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student findByEmail(String email);

    void saveStudent(Student student);
}