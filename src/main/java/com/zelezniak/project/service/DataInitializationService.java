package com.zelezniak.project.service;


import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Role;
import com.zelezniak.project.repository.CourseAuthorRepository;
import com.zelezniak.project.repository.RoleRepository;
import jakarta.annotation.PostConstruct;

import java.util.HashSet;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
final class DataInitializationService {

    private final RoleRepository roleRepository;
    private final CourseAuthorRepository authorRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    private void initializeRoles() {
        if (this.roleRepository.count() == 0L) {
            Role roleStudent = new Role("ROLE_STUDENT");
            Role roleTeacher = new Role("ROLE_TEACHER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            this.roleRepository.save(roleStudent);
            this.roleRepository.save(roleTeacher);
            this.roleRepository.save(roleAdmin);
            this.initializeAdmin(roleStudent, roleAdmin, roleTeacher);
        }

    }

    private void initializeAdmin(Role roleStudent, Role roleAdmin, Role roleTeacher) {
        CourseAuthor byEmail = this.authorRepository.findByEmail("admin@gmail.com");
        if (byEmail == null) {
            CourseAuthor admin = new CourseAuthor();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(this.encoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleStudent);
            roles.add(roleTeacher);
            roles.add(roleAdmin);
            admin.setRoles(roles);
            this.authorRepository.save(admin);
        }
    }
}