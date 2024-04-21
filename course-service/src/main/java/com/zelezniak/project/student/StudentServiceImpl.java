package com.zelezniak.project.student;


import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return this.studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student findByEmail(String email) {
        return studentRepository.findByUserCredentialsEmail(email);
    }

    @Transactional
    public void saveStudent(Student student) {
        if(student !=null){studentRepository.save(student);}
    }

    @Transactional
    public boolean existsByEmail(String email) {
        return studentRepository.existsByUserCredentialsEmail(email);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new UserException(UserError.USER_NOT_FOUND));
    }

}