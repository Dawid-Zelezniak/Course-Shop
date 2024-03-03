package com.zelezniak.project.service;


import java.util.List;

import com.zelezniak.project.entity.Student;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
import com.zelezniak.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

}