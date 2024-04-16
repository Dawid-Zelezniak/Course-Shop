package com.zelezniak.project.initialization;


import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.role.Role;
import com.zelezniak.project.author.CourseAuthorRepository;
import com.zelezniak.project.role.RoleRepository;
import com.zelezniak.project.valueobjects.UserCredentials;
import com.zelezniak.project.valueobjects.UserName;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
final class InitializationService {

    private final RoleRepository roleRepository;
    private final CourseAuthorRepository authorRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    private void initializeRoles() {
        if (roleRepository.count() == 0L) {
            Role roleStudent = new Role("ROLE_STUDENT");
            Role roleTeacher = new Role("ROLE_TEACHER");
            Role roleAdmin = new Role("ROLE_ADMIN");
            Set<Role> roles = new HashSet<>(List.of(roleStudent,roleTeacher,roleAdmin));
            roleRepository.saveAll(roles);
            initializeAdmin(roles);
        }
    }

    private void initializeAdmin(Set<Role> roles) {
        CourseAuthor byEmail = authorRepository.findByUserCredentialsEmail("admin@gmail.com");
        if (byEmail == null) {
            CourseAuthor admin = CourseAuthor.builder()
                    .userName(new UserName("Admin", "Admin"))
                    .userCredentials(new UserCredentials("admin@gmail.com", encoder.encode("admin123")))
                    .roles(roles)
                    .build();
            authorRepository.save(admin);
        }
    }
}