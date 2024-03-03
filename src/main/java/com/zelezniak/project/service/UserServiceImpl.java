package com.zelezniak.project.service;


import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Role;
import com.zelezniak.project.entity.Student;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
import com.zelezniak.project.repository.CourseAuthorRepository;
import com.zelezniak.project.repository.RoleRepository;
import com.zelezniak.project.repository.StudentRepository;
import com.zelezniak.project.user.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final StudentRepository studentRepository;
    private final CourseAuthorRepository authorRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String ROLE_STUDENT = "ROLE_STUDENT";
    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    @Transactional
    public void createNewUser(UserData user) {
        if (user != null) {
            validateIfUserExistsAndEmailFormat(user.getEmail());
            String role = user.getRole();
            if (role.equals(ROLE_STUDENT)) {
                this.studentRepository.save(this.newStudent(user));
            } else if (role.equals(ROLE_TEACHER)) {
                authorRepository.save(this.newAuthor(user));
            }
        }
    }

    private Student newStudent(UserData user) {
        Student studentToSave = setStudentData(user);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_STUDENT));
        studentToSave.setRoles(roles);
        return studentToSave;
    }

    private CourseAuthor newAuthor(UserData user) {
        CourseAuthor authorToSave = setAuthorData(user);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_STUDENT));
        roles.add(roleRepository.findByName(ROLE_TEACHER));
        authorToSave.setRoles(roles);
        return authorToSave;
    }

    private CourseAuthor setAuthorData(UserData user) {
        return CourseAuthor.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(encodePassword(user.getPassword()))
                .build();
    }

    private Student setStudentData(UserData user) {
        return Student.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(encodePassword(user.getPassword()))
                .build();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CourseAuthor author = authorRepository.findByEmail(username);
        if (author != null) {return buildUserDetails(author);}
        else {
            Student student = studentRepository.findByEmail(username);
            if (student != null) {return buildUserDetails(student);}
            else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
    }

    private UserDetails buildUserDetails(CourseAuthor author) {
        Collection<SimpleGrantedAuthority> authorities = author.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(author.getEmail(), author.getPassword(), authorities);
    }

    private UserDetails buildUserDetails(Student student) {
        Collection<SimpleGrantedAuthority> authorities = student.getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(student.getEmail(), student.getPassword(), authorities);
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    private void validateIfUserExistsAndEmailFormat(String email) {
        if (email.matches(UserService.EMAIL_PATTERN)) {
            if (studentRepository.existsByEmail(email) || authorRepository.existsByEmail(email)) {
                throw new CourseException(CustomErrors.USER_ALREADY_EXISTS);}
        } else {
            throw new CourseException(CustomErrors.EMAIL_IN_WRONG_FORMAT);
        }
    }
}