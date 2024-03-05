package com.zelezniak.project.service;


import com.zelezniak.project.entity.Student;
import com.zelezniak.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveStudent(Student student) {
        if(student !=null){studentRepository.save(student);}
    }

}