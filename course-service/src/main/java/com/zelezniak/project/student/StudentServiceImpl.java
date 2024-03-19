package com.zelezniak.project.student;


import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
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
        return studentRepository.findByEmail(email);
    }

    public void saveStudent(Student student) {
        if(student !=null){studentRepository.save(student);}
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()-> new CourseException(CustomErrors.USER_NOT_FOUND));
    }

}