package com.zelezniak.project.author;


import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import com.zelezniak.project.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class AuthorServiceImpl implements AuthorService {

    private final CourseAuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public CourseAuthor findByEmail(String email) {
        return authorRepository.findByUserCredentialsEmail(email);
    }

    @Transactional(readOnly = true)
    public List<CourseAuthor> getAllAuthors() {
        return authorRepository.getAllAuthorsWithoutAdmin("ROLE_ADMIN");
    }

    @Transactional
    public void saveAuthor(CourseAuthor author) {
    if(author!=null) {authorRepository.save(author);}
    }

    @Transactional
    public boolean existsByEmail(String email) {
        return authorRepository.existsByUserCredentialsEmail(email);
    }

    @Transactional(readOnly = true)
    public CourseAuthor findById(Long authorId) {
        return authorRepository.findByAuthorId(authorId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }
}