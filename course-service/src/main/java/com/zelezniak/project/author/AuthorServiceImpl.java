package com.zelezniak.project.author;


import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
final class AuthorServiceImpl implements AuthorService {

    private final CourseAuthorRepository authorRepository;

    public CourseAuthor findByEmail(String email) {
        return authorRepository.findByUserCredentialsEmail(email);
    }

    public List<CourseAuthor> getAllAuthors() {
        List<CourseAuthor> authors = authorRepository.findAll();
        //return only authors without admin people
        authors.removeIf(courseAuthor -> courseAuthor.getRoles()
                .stream()
                .anyMatch(role -> role.toString().contains("ADMIN")));
        return authors;
    }

    public void saveAuthor(CourseAuthor author) {
    if(author!=null) {authorRepository.save(author);}
    }

    public boolean existsByEmail(String email) {
        return authorRepository.existsByUserCredentialsEmail(email);
    }

    public CourseAuthor findById(Long authorId) {
        return authorRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }
}