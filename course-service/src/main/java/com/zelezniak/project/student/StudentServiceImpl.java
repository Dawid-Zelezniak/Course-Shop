package com.zelezniak.project.student;


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

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

}