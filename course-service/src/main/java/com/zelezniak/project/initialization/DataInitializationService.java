package com.zelezniak.project.initialization;


import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.role.Role;
import com.zelezniak.project.author.CourseAuthorRepository;
import com.zelezniak.project.role.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
final class DataInitializationService {

    private final RoleRepository roleRepository;
    private final CourseAuthorRepository authorRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    private void initializeRoles() {
        if (roleRepository.count() == 0L) {
            Role roleStudent = new Role("ROLE_STUDENT");
            Role roleTeacher = new Role("ROLE_TEACHER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            roleRepository.save(roleStudent);
            roleRepository.save(roleTeacher);
            roleRepository.save(roleAdmin);
            initializeAdmin(roleStudent, roleAdmin, roleTeacher);
        }
    }

    private void initializeAdmin(Role roleStudent, Role roleAdmin, Role roleTeacher) {
        CourseAuthor byEmail = authorRepository.findByEmail("admin@gmail.com");
        if (byEmail == null) {
            CourseAuthor admin = new CourseAuthor();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(encoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleStudent);
            roles.add(roleTeacher);
            roles.add(roleAdmin);
            admin.setRoles(roles);
            authorRepository.save(admin);
        }
    }
}