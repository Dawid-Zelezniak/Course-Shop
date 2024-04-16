package com.zelezniak.project.user;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

class UserDetailsBuilder {

    public static UserDetails buildUserDetails(CourseAuthor author) {
        Collection<SimpleGrantedAuthority> authorities = author.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(author.getUserCredentials().getEmail(),
                author.getUserCredentials().getPassword(), authorities);
    }

    public static UserDetails buildUserDetails(Student student) {
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(student.getRole().getName()));
        return new User(student.getUserCredentials().getEmail(), student.getUserCredentials().getPassword(), authorities);
    }
}
