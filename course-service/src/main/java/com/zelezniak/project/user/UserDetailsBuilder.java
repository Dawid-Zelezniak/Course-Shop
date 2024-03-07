package com.zelezniak.project.user;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

class UserDetailsBuilder {

    public static UserDetails buildUserDetails(CourseAuthor author) {
        Collection<SimpleGrantedAuthority> authorities = author.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(author.getEmail(), author.getPassword(), authorities);
    }

    public static UserDetails buildUserDetails(Student student) {
        Collection<SimpleGrantedAuthority> authorities = student.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(student.getEmail(), student.getPassword(), authorities);
    }
}
