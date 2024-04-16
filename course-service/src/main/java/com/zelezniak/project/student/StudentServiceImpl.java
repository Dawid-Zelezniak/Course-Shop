package com.zelezniak.project.student;


import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
final class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    public Student findByEmail(String email) {
        return studentRepository.findByUserCredentialsEmail(email);
    }

    public void saveStudent(Student student) {
        if(student !=null){studentRepository.save(student);}
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByUserCredentialsEmail(email);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new UserException(UserError.USER_NOT_FOUND));
    }

}